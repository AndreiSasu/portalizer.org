package org.portalizer.service.dto;

import java.util.Objects;
import java.util.UUID;

public class ReorderColumnsDTO {

    UUID boardId;
    int oldIndex;
    int newIndex;

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public void setOldIndex(int oldIndex) {
        this.oldIndex = oldIndex;
    }

    public void setNewIndex(int newIndex) {
        this.newIndex = newIndex;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public int getOldIndex() {
        return oldIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReorderColumnsDTO that = (ReorderColumnsDTO) o;
        return oldIndex == that.oldIndex &&
            newIndex == that.newIndex &&
            Objects.equals(boardId, that.boardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, oldIndex, newIndex);
    }

    @Override
    public String toString() {
        return "ReorderColumnsDTO{" +
            "boardId=" + boardId +
            ", oldIndex=" + oldIndex +
            ", newIndex=" + newIndex +
            '}';
    }
}
