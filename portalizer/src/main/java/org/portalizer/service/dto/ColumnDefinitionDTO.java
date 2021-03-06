package org.portalizer.service.dto;

import java.io.Serializable;
import java.util.UUID;

public class ColumnDefinitionDTO implements Serializable {

    private UUID key;
    private String title;
    private int priority;

    public ColumnDefinitionDTO() {}

    public ColumnDefinitionDTO(UUID key, String title, int priority) {
        this.key = key;
        this.title = title;
        this.priority = priority;
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "ColumnDefinitionDTO{" +
            "key=" + key +
            ", title='" + title + '\'' +
            ", priority=" + priority +
            '}';
    }
}
