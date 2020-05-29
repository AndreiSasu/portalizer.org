package org.portalizer.demodata.steps;

import com.github.javafaker.Faker;
import org.portalizer.demodata.UserFactory;
import org.portalizer.domain.Board;
import org.portalizer.domain.User;
import org.portalizer.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RandomDbOwnerStep implements OwnerStep {

    private final int maxUsers;
    private final Faker faker;
    private final UserService userService;
    private final UserFactory userFactory;

    public RandomDbOwnerStep(@Value("${portalizer.demo.max-users}") int maxUsers, Faker faker, final UserFactory userFactory, UserService userService) {
        this.faker = faker;
        this.userService = userService;
        this.maxUsers = maxUsers;
        this.userFactory = userFactory;
    }

    @Override
    public void apply(Board board) {
        final Optional<User> maybeUser = userService.getUserWithAuthorities((long) faker.number().numberBetween(0, maxUsers));
        final User user = maybeUser.orElseGet(userFactory::newUser);
        board.setOwner(user);
    }
}
