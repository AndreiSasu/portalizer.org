package com.mycompany.myapp.service.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BoardColumn} entity.
 */
public class BoardColumnDTO implements Serializable {

    private Long id;

    private Integer color;

    @NotNull
    @Max(value = 4)
    private Integer columnId;

    @NotNull
    @Size(min = 1)
    private String value;


    private Long boardSummaryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getBoardSummaryId() {
        return boardSummaryId;
    }

    public void setBoardSummaryId(Long boardSummaryId) {
        this.boardSummaryId = boardSummaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoardColumnDTO boardColumnDTO = (BoardColumnDTO) o;
        if (boardColumnDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), boardColumnDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BoardColumnDTO{" +
            "id=" + getId() +
            ", color=" + getColor() +
            ", columnId=" + getColumnId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
