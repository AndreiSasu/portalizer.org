package org.portalizer.demodata;

import org.portalizer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
@Profile("demo")
@Order(0)
public class SetupDemoUsers implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SetupDemoUsers.class);

    private int maxUsers;
    private UserService userService;
    private UserFactory userFactory;

    public SetupDemoUsers(@Value("${portalizer.demo.max-users}") int maxUsers, final UserFactory userFactory, UserService userService) {
        this.maxUsers = maxUsers;
        this.userService = userService;
        this.userFactory = userFactory;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Adding {} users.", maxUsers);
        for(int i = 0; i < maxUsers; i++) {
            this.userFactory.newUser();
        }

        logger.info("Added {} users. Total users: {}", maxUsers, userService.getAllManagedUsers(Pageable.unpaged()).getTotalElements());
    }
}
