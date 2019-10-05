package org.portalizer.retro.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A BoardColumn.
 */
@Entity
@Table(name = "board_column")
public class BoardColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "color")
    private Integer color;

    @NotNull
    @Max(value = 4)
    @Column(name = "column_id", nullable = false)
    private Integer columnId;

    @NotNull
    @Size(min = 1)
    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "board_summary_id", referencedColumnName = "id")
    private BoardSummary boardSummary;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getColor() {
        return color;
    }

    public BoardColumn color(Integer color) {
        this.color = color;
        return this;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public BoardColumn columnId(Integer columnId) {
        this.columnId = columnId;
        return this;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public String getValue() {
        return value;
    }

    public BoardColumn value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BoardSummary getBoardSummary() {
        return boardSummary;
    }

    public BoardColumn boardSummary(BoardSummary boardSummary) {
        this.boardSummary = boardSummary;
        return this;
    }

    public void setBoardSummary(BoardSummary boardSummary) {
        this.boardSummary = boardSummary;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardColumn)) {
            return false;
        }
        return id != null && id.equals(((BoardColumn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BoardColumn{" +
            "id=" + getId() +
            ", color=" + getColor() +
            ", columnId=" + getColumnId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
