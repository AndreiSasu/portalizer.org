package org.portalizer.service.dto;

import java.util.Objects;
import java.util.UUID;

public class AddColumnDTO {
    private UUID boardId;
    private String title;

    public AddColumnDTO() {}

    public AddColumnDTO(UUID boardId, String title) {
        this.boardId = boardId;
        this.title = title;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddColumnDTO that = (AddColumnDTO) o;
        return Objects.equals(boardId, that.boardId) &&
            Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, title);
    }

    @Override
    public String toString() {
        return "AddColumnDTO{" +
            "boardId=" + boardId +
            ", title='" + title + '\'' +
            '}';
    }
}
