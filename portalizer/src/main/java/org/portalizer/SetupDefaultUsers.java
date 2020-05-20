package org.portalizer;

import org.portalizer.domain.User;
import org.portalizer.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Profile("prod")
public class SetupDefaultUsers implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SetupDefaultUsers.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Environment environment;

    public SetupDefaultUsers(UserRepository userRepository, PasswordEncoder passwordEncoder, Environment environment) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        final String defaultAdminPass = Optional.ofNullable(environment.getProperty("ADMIN_PASS"))
            .orElseThrow(() -> new IllegalArgumentException("Cannot set default admin password, did you set ADMIN_PASS env variable?"));

        final String defaultUserPass = Optional.ofNullable(environment.getProperty("USER_PASS"))
            .orElseThrow(() -> new IllegalArgumentException("Cannot set default user password, did you set USER_PASS env variable?"));

        User admin = this.userRepository.findOneByLogin("admin").get();
        admin.setActivated(true);
        admin.setPassword(passwordEncoder.encode(defaultAdminPass));
        userRepository.save(admin);
        logger.info("Set admin password.");

        User user = this.userRepository.findOneByLogin("user").get();
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode(defaultUserPass));
        userRepository.save(user);
        logger.info("Set user password.");
    }
}
