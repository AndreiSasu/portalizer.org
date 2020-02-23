package org.portalizer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

@Deprecated
public enum ColumnType {
    MAD("Mad"),
    SAD("Sad"),
    GLAD("Glad"),

    WENT_WELL("Went Well"),
    TO_IMPROVE("To Improve"),
    ACTION_ITEMS("Action Items"),

    START("Start"),
    STOP("Stop"),
    CONTINUE("Continue"),

    LIKED("Liked"),
    LEARNED("Learned"),
    LACKED("Lacked"),
    LONGED_FOR("Longed For"),

    TO_DISCUSS("To Discuss"),
    DISCUSSING("Discussing"),
    DISCUSSED("Discussed"),

    DROP("Drop"),
    ADD("Add"),
    KEEP("Keep"),

    LESS("Less"),
    MORE("More"),

    IMPROVE("Improve");


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
