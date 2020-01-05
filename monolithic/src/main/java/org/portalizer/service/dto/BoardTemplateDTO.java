package org.portalizer.service.dto;

import java.util.List;

public class BoardTemplateDTO {
    private String key;
    private List<ColumnDefinitionDTO> boardColumns;
    private String description;

    public BoardTemplateDTO(String key, List<ColumnDefinitionDTO> boardColumns, String description) {
        this.key = key;
        this.boardColumns = boardColumns;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public List<ColumnDefinitionDTO> getBoardColumns() {
        return boardColumns;
    }
}
