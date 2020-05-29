package org.portalizer.service.dto;

import org.portalizer.config.Constants;

import org.portalizer.domain.Authority;
import org.portalizer.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    private boolean socialLogin = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.getActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.socialLogin = user.isSocialLogin();
        this.authorities = user.getAuthorities().stream()
            .map(Authority::getName)
            .collect(Collectors.toSet());
    }

    public boolean isSocialLogin() {
        return socialLogin;
    }

    public void setSocialLogin(boolean socialLogin) {
        this.socialLogin = socialLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            "}";
    }


    public static final class UserDTOBuilder {
        private Long id;
        private String login;
        private String firstName;
        private String lastName;
        private String email;
        private String imageUrl;
        private boolean activated = false;
        private boolean socialLogin = false;
        private String langKey;
        private String createdBy;
        private Instant createdDate;
        private String lastModifiedBy;
        private Instant lastModifiedDate;
        private Set<String> authorities;

        private UserDTOBuilder() {
        }

        public static UserDTOBuilder anUserDTO() {
            return new UserDTOBuilder();
        }

        public UserDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder withLogin(String login) {
            this.login = login;
            return this;
        }

        public UserDTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDTOBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public UserDTOBuilder withActivated(boolean activated) {
            this.activated = activated;
            return this;
        }

        public UserDTOBuilder withSocialLogin(boolean socialLogin) {
            this.socialLogin = socialLogin;
            return this;
        }

        public UserDTOBuilder withLangKey(String langKey) {
            this.langKey = langKey;
            return this;
        }

        public UserDTOBuilder withCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public UserDTOBuilder withCreatedDate(Instant createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public UserDTOBuilder withLastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
            return this;
        }

        public UserDTOBuilder withLastModifiedDate(Instant lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public UserDTOBuilder withAuthorities(Set<String> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UserDTO build() {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userDTO.setLogin(login);
            userDTO.setFirstName(firstName);
            userDTO.setLastName(lastName);
            userDTO.setEmail(email);
            userDTO.setImageUrl(imageUrl);
            userDTO.setActivated(activated);
            userDTO.setSocialLogin(socialLogin);
            userDTO.setLangKey(langKey);
            userDTO.setCreatedBy(createdBy);
            userDTO.setCreatedDate(createdDate);
            userDTO.setLastModifiedBy(lastModifiedBy);
            userDTO.setLastModifiedDate(lastModifiedDate);
            userDTO.setAuthorities(authorities);
            return userDTO;
        }
    }
}
