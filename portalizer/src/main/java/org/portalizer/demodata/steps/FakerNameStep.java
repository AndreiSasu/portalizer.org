package org.portalizer.demodata.steps;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class FakerNameStep implements NameStep {
    private final Faker faker;

    public FakerNameStep(Faker faker) {
        this.faker = faker;
    }

    @Override
    public String apply() {
        return faker.company().bs();
    }
}
