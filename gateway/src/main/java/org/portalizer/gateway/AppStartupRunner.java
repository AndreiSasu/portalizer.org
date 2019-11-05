package org.portalizer.gateway;

import org.portalizer.gateway.domain.User;
import org.portalizer.gateway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class AppStartupRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Environment environment;
    public AppStartupRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, Environment environment) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User admin = this.userRepository.findOneByLogin("admin").get();
        admin.setPassword(passwordEncoder.encode(environment.getProperty("ADMIN_PASS")));
        userRepository.save(admin);
        logger.info("Set admin password.");

        User user = this.userRepository.findOneByLogin("user").get();
        user.setPassword(passwordEncoder.encode(environment.getProperty("USER_PASS")));
        userRepository.save(user);
        logger.info("Set user password.");

    }
}
