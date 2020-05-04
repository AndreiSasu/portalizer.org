package org.portalizer.service.dto;

import java.util.UUID;

public class UpdateBoardDTO {

    private UUID id;
    private String name;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
