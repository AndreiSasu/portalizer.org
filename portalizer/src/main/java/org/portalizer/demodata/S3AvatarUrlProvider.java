package org.portalizer.demodata;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3AvatarUrlProvider implements AvatarUrlProvider {

    final String baseUrl;
    final Faker faker;

    public S3AvatarUrlProvider(@Value("${portalizer.demo.avatars.base-url}") String baseUrl, Faker faker) {
        this.baseUrl = baseUrl;
        this.faker = faker;
    }

    @Override
    public String get() {
        return baseUrl + faker.number().numberBetween(1, 180) + ".svg";
    }
}
