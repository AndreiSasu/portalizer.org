package org.portalizer.demodata;

import com.github.javafaker.Faker;
import org.portalizer.domain.User;
import org.portalizer.service.UserService;
import org.portalizer.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserFactory {

    private UserService userService;
    private Faker faker;
    private AvatarUrlProvider avatarUrlProvider;

    public UserFactory(AvatarUrlProvider avatarUrlProvider, Faker faker, UserService userService) {
        this.userService = userService;
        this.faker = faker;
        this.avatarUrlProvider = avatarUrlProvider;
    }

    public User newUser() {
        final Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        final UserDTO userDTO = UserDTO.UserDTOBuilder
            .anUserDTO()
            .withActivated(true)
            .withLogin(faker.name().username())
            .withFirstName(faker.name().firstName())
            .withLastName(faker.name().lastName())
            .withSocialLogin(true)
            .withEmail(faker.internet().emailAddress())
            .withAuthorities(roles)
            .withImageUrl(avatarUrlProvider.get())
            .build();
        return userService.createUser(userDTO);
    }
}
