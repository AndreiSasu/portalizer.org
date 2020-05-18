package org.portalizer.demodata.steps;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class FakerDescriptionStep implements DescriptionStep {

    private final Faker faker;

    public FakerDescriptionStep(Faker faker) {
        this.faker = faker;
    }

    @Override
    public String apply() {
        return faker.company().catchPhrase();
    }
}
