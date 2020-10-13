package org.portalizer.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateCardDTO {
    @NotNull
    private UUID boardId;

    @NotBlank
    private String text;

    @NotNull private UUID columnKey;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(UUID columnKey) {
        this.columnKey = columnKey;
    }
}
