package org.portalizer.config;

import org.portalizer.security.*;
import org.portalizer.security.jwt.*;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@EnableOAuth2Client
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
@Profile("!enable-auth")
public class DemoSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;
    private final SecurityProblemSupport problemSupport;

    static final String GOOGLE_LOGIN_URL = "/login/google";
    static final String GITHUB_LOGIN_URL = "/login/github";

    private final WebOAuth2ConfigHelper webOAuth2ConfigHelper;
    private final OAuth2ClientContext oauth2ClientContext;


    public DemoSecurityConfiguration(TokenProvider tokenProvider, CorsFilter corsFilter, SecurityProblemSupport problemSupport,
                                     WebOAuth2ConfigHelper webOAuth2ConfigHelper, OAuth2ClientContext oauth2ClientContext) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
        this.webOAuth2ConfigHelper = webOAuth2ConfigHelper;
        this.oauth2ClientContext = oauth2ClientContext;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/h2-console/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(ssoFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data: *.googleusercontent.com *.githubusercontent.com; font-src 'self' data:")
        .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
        .and()
            .frameOptions()
            .deny()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and()
            .authorizeRequests()
            .antMatchers("/api/retro/**").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/websocket/**").permitAll()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .and()
            .httpBasic()
        .and()
            .apply(securityConfigurerAdapter());
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }


    private Filter ssoFilter() {
        CompositeFilter compositeFilter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(googleOAuth2AuthProcessingFilter());
        filters.add(githubOAuth2AuthProcessingFilter());
        compositeFilter.setFilters(filters);
        return compositeFilter;
    }

    private Filter googleOAuth2AuthProcessingFilter() {
        WebOAuth2AuthProcessingFilter webOAuth2AuthProcessingFilter =
            new WebOAuth2AuthProcessingFilter(GOOGLE_LOGIN_URL, googleClientResource());
        webOAuth2AuthProcessingFilter.init();
        return webOAuth2AuthProcessingFilter;
    }

    private Filter githubOAuth2AuthProcessingFilter() {
        WebOAuth2AuthProcessingFilter webOAuth2AuthProcessingFilter =
            new WebOAuth2AuthProcessingFilter(GITHUB_LOGIN_URL, githubClientResource());
        webOAuth2AuthProcessingFilter.init();
        return webOAuth2AuthProcessingFilter;
    }

    @Bean
    @ConfigurationProperties("google")
    ClientResources googleClientResource() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("github")
    ClientResources githubClientResource() {
        return new ClientResources();
    }

    @Bean
    FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(
        OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    private class WebOAuth2AuthProcessingFilter extends OAuth2ClientAuthenticationProcessingFilter {

        private final ClientResources clientResources;

        WebOAuth2AuthProcessingFilter(
            String defaultFilterProcessesUrl, ClientResources clientResources) {
            super(defaultFilterProcessesUrl);
            this.clientResources = clientResources;
        }

        void init() {
            setAuthenticationSuccessHandler(webOAuth2ConfigHelper.getWebOAuth2AuthSuccessHandler());
            setRestTemplate(new OAuth2RestTemplate(clientResources.getClient(), oauth2ClientContext));
            setTokenServices(tokenServices());
        }

        private ResourceServerTokenServices tokenServices() {
            UserInfoTokenServices tokenServices =
                new UserInfoTokenServices(
                    clientResources.getResource().getUserInfoUri(),
                    clientResources.getClient().getClientId());
            tokenServices.setPrincipalExtractor(webOAuth2ConfigHelper.getWebOAuth2PrincipalExtractor());
            tokenServices.setAuthoritiesExtractor(
                webOAuth2ConfigHelper.getWebOAuth2AuthoritiesExtractor());
            return tokenServices;
        }
    }

    private static final class ClientResources {

        @NestedConfigurationProperty
        private final AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

        @NestedConfigurationProperty
        private final ResourceServerProperties resource = new ResourceServerProperties();

        public AuthorizationCodeResourceDetails getClient() {
            return client;
        }

        public ResourceServerProperties getResource() {
            return resource;
        }
    }
}
