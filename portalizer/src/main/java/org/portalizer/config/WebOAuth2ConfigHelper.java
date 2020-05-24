package org.portalizer.config;

import org.portalizer.domain.User;
import org.portalizer.service.UserService;
import org.portalizer.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WebOAuth2ConfigHelper {

    private final WebOAuth2AuthoritiesExtractor webOAuth2AuthoritiesExtractor;
    private final WebOAuth2PrincipalExtractor webOAuth2PrincipalExtractor;
    private final WebOAuth2AuthenticationSuccessHandler webOAuth2AuthSuccessHandler;


    private final Logger log = LoggerFactory.getLogger(WebOAuth2ConfigHelper.class);

    public WebOAuth2ConfigHelper(WebOAuth2AuthoritiesExtractor webOAuth2AuthoritiesExtractor,
                                 WebOAuth2PrincipalExtractor webOAuth2PrincipalExtractor,
                                 WebOAuth2AuthenticationSuccessHandler webOAuth2AuthSuccessHandler) {
        this.webOAuth2AuthoritiesExtractor = webOAuth2AuthoritiesExtractor;
        this.webOAuth2PrincipalExtractor = webOAuth2PrincipalExtractor;
        this.webOAuth2AuthSuccessHandler = webOAuth2AuthSuccessHandler;
    }

    public WebOAuth2AuthoritiesExtractor getWebOAuth2AuthoritiesExtractor() {
        return webOAuth2AuthoritiesExtractor;
    }

    public WebOAuth2PrincipalExtractor getWebOAuth2PrincipalExtractor() {
        return webOAuth2PrincipalExtractor;
    }

    public WebOAuth2AuthenticationSuccessHandler getWebOAuth2AuthSuccessHandler() {
        return webOAuth2AuthSuccessHandler;
    }

    private static String getUserId(Map<String, Object> map) {
        HttpServletRequest currentRequest =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = currentRequest.getRequestURI();
        String userId = null;

        if (requestURI.equals(DemoSecurityConfiguration.GOOGLE_LOGIN_URL)) {
            userId = String.valueOf(map.get("sub"));
        } else if (requestURI.equals(DemoSecurityConfiguration.GITHUB_LOGIN_URL)) {
            userId = String.valueOf(map.get("id"));
        }

        if (userId == null) {
            throw new BadCredentialsException("User-Id could not be determined.");
        }

        return userId;
    }

    private static UserDTO getUserDTO(Map<String, Object> map) {
        HttpServletRequest currentRequest =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = currentRequest.getRequestURI();

        final UserDTO userDTO = new UserDTO();
        if (requestURI.equals(DemoSecurityConfiguration.GITHUB_LOGIN_URL)) {
            final String id = String.valueOf(map.get("id"));
            final String loginName = map.get("login").toString().toLowerCase();
            userDTO.setLogin(id + "_" + loginName);
            userDTO.setFirstName(map.get("name").toString());
            userDTO.setImageUrl(map.get("avatar_url").toString());
        }
        if(requestURI.equalsIgnoreCase(DemoSecurityConfiguration.GOOGLE_LOGIN_URL)) {
            final String id = String.valueOf(map.get("sub"));
            final String loginName = map.get("given_name").toString().toLowerCase();
            userDTO.setLogin(id + "_" + loginName);
            userDTO.setFirstName(String.valueOf(map.get("given_name")));
            userDTO.setLastName(String.valueOf(map.get("family_name")));
            userDTO.setImageUrl(String.valueOf(map.get("picture")));
        }

        userDTO.setAuthorities(new HashSet<>(Arrays.asList("ROLE_USER")));
        return userDTO;
    }

    @Component
    private static class WebOAuth2AuthoritiesExtractor implements AuthoritiesExtractor {

        private final UserService appUserService;

        private final Logger log = LoggerFactory.getLogger(WebOAuth2AuthoritiesExtractor.class);

        public WebOAuth2AuthoritiesExtractor(UserService appUserService) {
            this.appUserService = appUserService;
        }

        @Override
        public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
            final UserDTO userDTO = getUserDTO(map);
            Optional<User> optionalAppUser = appUserService.getUserWithAuthoritiesByLogin(userDTO.getLogin());
            if (!optionalAppUser.isPresent()) {
                return Collections.emptyList();
            }

            return optionalAppUser.get().getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        }
    }

    @Component
    private static class WebOAuth2PrincipalExtractor implements PrincipalExtractor {

        private final UserService appUserService;
        private final UserDetailsService userDetailsService;
        private final Logger log = LoggerFactory.getLogger(WebOAuth2PrincipalExtractor.class);

        public WebOAuth2PrincipalExtractor(UserService appUserService, UserDetailsService userDetailsService) {
            this.appUserService = appUserService;
            this.userDetailsService = userDetailsService;
        }

        @Override
        public Object extractPrincipal(Map<String, Object> map) {
            final UserDTO userDTO = getUserDTO(map);
            Optional<User> maybeAppUser = appUserService.getUserWithAuthoritiesByLogin(userDTO.getLogin());
            final User appUser = maybeAppUser.orElseGet(() -> appUserService.createUser(userDTO));
            return userDetailsService.loadUserByUsername(appUser.getLogin());
        }
    }

    @Component
    private static class WebOAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
            setDefaultTargetUrl("/");
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
