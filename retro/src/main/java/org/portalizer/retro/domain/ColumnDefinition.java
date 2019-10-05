package org.portalizer.retro.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "column_definition")
public class ColumnDefinition implements Comparable<ColumnDefinition> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private ColumnType columnType;

    @NotNull
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnDefinition that = (ColumnDefinition) o;
        return columnType == that.columnType &&
            Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ColumnDefinition{" +
            "columnType=" + columnType +
            ", title='" + title + '\'' +
            '}';
    }

    @Override
    public int compareTo(ColumnDefinition columnDefinition) {
        int thisOrdinal = this.getColumnType().ordinal();
        int otherOrdinal = columnDefinition.getColumnType().ordinal();
        if(thisOrdinal == otherOrdinal) {
            return 0;
        }
        if(thisOrdinal > otherOrdinal){
            return 1;
        }
        return -1;
    }
}
