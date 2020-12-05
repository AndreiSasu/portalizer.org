package org.portalizer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "board_template_column_definition")
public class BoardTemplateColumnDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int priority = 0;

    @NotNull
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_template_id", referencedColumnName = "id")
    private BoardTemplate boardTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public BoardTemplate getBoardTemplate() {
        return boardTemplate;
    }

    public void setBoardTemplate(BoardTemplate boardTemplate) {
        this.boardTemplate = boardTemplate;
    }
}
