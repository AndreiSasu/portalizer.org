package org.portalizer.service.dto;

public class CounterDTO {

    private final String name;
    private final long value;

    public CounterDTO(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public long getValue() {
        return value;
    }
}
