package org.portalizer.retro.domain;

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
}
