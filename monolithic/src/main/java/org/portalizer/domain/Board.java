package org.portalizer.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "board")
@Indexed
public class Board implements Serializable {

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
    @Field(termVector = TermVector.YES)
    private String name;

    @Field(termVector = TermVector.YES)
    @Column(columnDefinition = "VARCHAR(2048)")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn
    private List<ColumnDefinition> columnDefinitions;

    @OneToMany(targetEntity = InformationCard.class, mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InformationCard> informationCards;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<InformationCard> getInformationCards() {
        return informationCards;
    }

    public void setInformationCards(List<InformationCard> informationCards) {
        this.informationCards = informationCards;
    }

    public List<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id) &&
            Objects.equals(columnDefinitions, board.columnDefinitions) &&
            Objects.equals(informationCards, board.informationCards);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Board{" +
            "id=" + id +
            ", columnDefinitions=" + columnDefinitions +
            '}';
    }
}
