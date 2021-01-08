package org.portalizer.service.dto;

import java.util.List;

public class BoardTemplateDTO {
    private String title;
    private List<BoardTemplateColumnDefinitionDTO> boardColumns;
    private String description;

    public BoardTemplateDTO(String title, List<BoardTemplateColumnDefinitionDTO> boardColumns, String description) {
        this.title = title;
        this.boardColumns = boardColumns;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public List<BoardTemplateColumnDefinitionDTO> getBoardColumns() {
        return boardColumns;
    }
}
