package org.portalizer.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class InformationCardDTO implements Serializable {

    private UUID id;

    @NotNull private UUID boardId;

    @NotBlank private String text;

    @NotNull private UUID columnKey;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(UUID columnKey) {
        this.columnKey = columnKey;
    }

    @Override
    public String toString() {
        return "InformationCardDTO{" +
            "id=" + id +
            ", boardId=" + boardId +
            ", text='" + text + '\'' +
            ", columnKey=" + columnKey +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
