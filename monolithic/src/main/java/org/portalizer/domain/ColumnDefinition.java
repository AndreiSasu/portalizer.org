package org.portalizer.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "column_definition")
public class ColumnDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private ColumnType columnType;

    @NotNull
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return "ColumnDefinition{" +
            "id=" + id +
            ", columnType=" + columnType +
            ", title='" + title + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnDefinition that = (ColumnDefinition) o;
        return Objects.equals(id, that.id) &&
            columnType == that.columnType &&
            Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
