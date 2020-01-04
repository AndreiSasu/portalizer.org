package org.portalizer.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A BoardSummary.
 */
@Entity
@Table(name = "board_summary")
public class BoardSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "archived")
    private Boolean archived;

    @NotNull
    @Size(min = 1)
    @Column(name = "board_name", nullable = false)
    private String boardName;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "board_id", length = 36, nullable = false, updatable = false, unique = true)
    private UUID boardId;

    @OneToMany(targetEntity = BoardColumn.class, fetch = FetchType.EAGER, mappedBy = "boardSummary", cascade = CascadeType.ALL)
    private Set<BoardColumn> boardColumns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isArchived() {
        return archived;
    }

    public BoardSummary archived(Boolean archived) {
        this.archived = archived;
        return this;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public String getBoardName() {
        return boardName;
    }

    public BoardSummary boardName(String boardName) {
        this.boardName = boardName;
        return this;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public BoardSummary dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public BoardSummary boardId(UUID boardId) {
        this.boardId = boardId;
        return this;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public Set<BoardColumn> getBoardColumns() {
        return boardColumns;
    }

    public BoardSummary boardColumns(Set<BoardColumn> boardColumns) {
        this.boardColumns = boardColumns;
        return this;
    }

    public BoardSummary addBoardColumns(BoardColumn boardColumn) {
        this.boardColumns.add(boardColumn);
        boardColumn.setBoardSummary(this);
        return this;
    }

    public BoardSummary removeBoardColumns(BoardColumn boardColumn) {
        this.boardColumns.remove(boardColumn);
        boardColumn.setBoardSummary(null);
        return this;
    }

    public void setBoardColumns(Set<BoardColumn> boardColumns) {
        this.boardColumns = boardColumns;
        if(null != this.boardColumns)
            this.boardColumns.forEach(boardColumn -> {
                boardColumn.setBoardSummary(this);
            });
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardSummary)) {
            return false;
        }
        return id != null && id.equals(((BoardSummary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BoardSummary{" +
            "id=" + getId() +
            ", archived='" + isArchived() + "'" +
            ", boardName='" + getBoardName() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", boardId='" + getBoardId() + "'" +
            "}";
    }
}