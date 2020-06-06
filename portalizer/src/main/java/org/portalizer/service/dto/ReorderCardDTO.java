package org.portalizer.service.dto;

import java.util.UUID;

public class ReorderCardDTO {
    private int oldIndex;
    private int newIndex;
    private UUID oldColumn;
    private UUID newColumn;

    public int getOldIndex() {
        return oldIndex;
    }

    public void setOldIndex(int oldIndex) {
        this.oldIndex = oldIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public void setNewIndex(int newIndex) {
        this.newIndex = newIndex;
    }

    public UUID getOldColumn() {
        return oldColumn;
    }

    public void setOldColumn(UUID oldColumn) {
        this.oldColumn = oldColumn;
    }

    public UUID getNewColumn() {
        return newColumn;
    }

    public void setNewColumn(UUID newColumn) {
        this.newColumn = newColumn;
    }
}
