package org.portalizer.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "column_definition")
public class ColumnDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Type(type="uuid-char")
    @Column(name = "column_key")
    private UUID key;

    private int priority = 0;

    @NotNull
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private Board board;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return priority == that.priority &&
            Objects.equals(id, that.id) &&
            Objects.equals(key, that.key) &&
            Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ColumnDefinition{" +
            "id=" + id +
            ", columnKey=" + key +
            ", priority=" + priority +
            ", title='" + title + '\'' +
            '}';
    }

    @PrePersist
    public void prePersist() {
        if(null == key) {
            this.key = UUID.randomUUID();
        }
    }

}
