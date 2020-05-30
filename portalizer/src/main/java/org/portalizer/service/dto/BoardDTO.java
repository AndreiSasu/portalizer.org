package org.portalizer.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BoardDTO implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<ColumnDefinitionDTO> columnDefinitions;
    private List<InformationCardDTO> informationCards;
    private UserDTO owner;

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ColumnDefinitionDTO> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(List<ColumnDefinitionDTO> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    public List<InformationCardDTO> getInformationCards() {
        return informationCards;
    }

    public void setInformationCards(List<InformationCardDTO> informationCards) {
        this.informationCards = informationCards;
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
            "id=" + id +
            ", columnDefinitions=" + columnDefinitions +
            ", informationCards=" + informationCards +
            '}';
    }
}
