package org.portalizer.retro.service.dto;

import org.portalizer.retro.domain.ColumnType;

import java.io.Serializable;

public class ColumnDefinitionDTO implements Serializable, Comparable<ColumnDefinitionDTO> {
    private ColumnType columnType;
    private String title;

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ColumnDefinitionDTO{" +
            "columnType=" + columnType +
            ", title='" + title + '\'' +
            '}';
    }

    @Override
    public int compareTo(ColumnDefinitionDTO columnDefinitionDTO) {
        int thisOrdinal = this.getColumnType().ordinal();
        int otherOrdinal = columnDefinitionDTO.getColumnType().ordinal();
        if(thisOrdinal == otherOrdinal) {
            return 0;
        }
        if(thisOrdinal > otherOrdinal){
            return 1;
        }
        return -1;
    }
}
