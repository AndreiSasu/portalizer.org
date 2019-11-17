package org.portalizer.retro.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ColumnType {
    MAD("Mad"),
    SAD("Sad"),
    GLAD("Glad"),
    WENT_WELL("Went Well"),
    TO_IMPROVE("To Improve"),
    ACTION_ITEMS("Action Items");

    private final String name;

    ColumnType(final String name) {
        this.name = name;
    }

    @JsonCreator
    public static ColumnType ofValue(String value) {
        final String sanitized = value.trim().replaceAll(" ", "_");
        return ColumnType.valueOf(sanitized.toUpperCase());
    }
}
