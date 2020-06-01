package org.portalizer.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Profiles {

    private final List<String> currentProfiles;
    public Profiles(Environment environment) {
        this.currentProfiles = Arrays.asList(environment.getActiveProfiles());
    }

    boolean contains(final String profile) {
        return this.currentProfiles.contains(profile);
    }

    boolean doesNotContain(final String profile) {
        return !this.currentProfiles.contains(profile);
    }

    boolean containsAny(final String... profiles) {
        return this.currentProfiles.containsAll(Arrays.asList(profiles));
    }

    boolean doesNotContainAny(final String... profiles) {
        return !this.currentProfiles.containsAll(Arrays.asList(profiles));
    }
}
