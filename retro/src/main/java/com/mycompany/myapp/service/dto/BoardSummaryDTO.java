package com.mycompany.myapp.service.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BoardSummary} entity.
 */
public class BoardSummaryDTO implements Serializable {

    private Boolean archived;

    @NotNull
    @Size(min = 1)
    private String boardName;

    private LocalDate dateCreated;

    @NotNull
    private UUID boardId;

    private Set<BoardColumnDTO> boardColumns;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public Boolean getArchived() {
        return archived;
    }

    public Set<BoardColumnDTO> getBoardColumns() {
        return boardColumns;
    }

    public void setBoardColumns(Set<BoardColumnDTO> boardColumns) {
        this.boardColumns = boardColumns;
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
        if (boardSummaryDTO.getBoardId() == null || getBoardId() == null) {
            return false;
        }
        return Objects.equals(getBoardId(), boardSummaryDTO.getBoardId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBoardId());
    }

    @Override
    public String toString() {
        return "BoardSummaryDTO{" +
            ", archived=" + archived +
            ", boardName='" + boardName + '\'' +
            ", dateCreated=" + dateCreated +
            ", boardId=" + boardId +
            ", boardColumns=" + boardColumns +
            '}';
    }
}
