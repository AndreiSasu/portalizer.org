package org.portalizer.retro.service.dto;

import org.portalizer.retro.domain.ColumnType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InformationCardDTO implements Serializable {
    private String text;
    private ColumnType columnType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
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

    @Override
    public String toString() {
        return "InformationCardDTO{" +
            "text='" + text + '\'' +
            ", columnType=" + columnType +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
