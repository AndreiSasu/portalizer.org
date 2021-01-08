package org.portalizer.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "board_template", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
public class BoardTemplate {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type="uuid-char")
    private UUID id;

    @NotEmpty
    private String title;

    /**
     * the Board associated with this board template.
     * should only allow 1 template association / board.
     */
    @Type(type="uuid-char")
    private UUID boardId;

    @NotEmpty
    @Column(columnDefinition = "VARCHAR(2048)")
    private String description;

    @OrderBy("priority")
    @OneToMany(targetEntity = BoardTemplateColumnDefinition.class, mappedBy = "boardTemplate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BoardTemplateColumnDefinition> columnDefinitions;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BoardTemplateColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(List<BoardTemplateColumnDefinition> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }
}
