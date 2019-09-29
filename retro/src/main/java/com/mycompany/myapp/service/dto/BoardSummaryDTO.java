package com.mycompany.myapp.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BoardSummary} entity.
 */
public class BoardSummaryDTO implements Serializable {

    private Long id;

    private Boolean archived;

    @NotNull
    @Size(min = 1)
    private String boardName;

    private LocalDate dateCreated;

    Set<BoardColumnDTO> boardColumns;

    public Boolean getArchived() {
        return archived;
    }

    public Set<BoardColumnDTO> getBoardColumns() {
        return boardColumns;
    }

    public void setBoardColumns(Set<BoardColumnDTO> boardColumns) {
        this.boardColumns = boardColumns;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoardSummaryDTO boardSummaryDTO = (BoardSummaryDTO) o;
        if (boardSummaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), boardSummaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BoardSummaryDTO{" +
            "id=" + getId() +
            ", archived='" + isArchived() + "'" +
            ", boardName='" + getBoardName() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
