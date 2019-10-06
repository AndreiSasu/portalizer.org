package org.portalizer.retro.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.UUID;

@Entity
@Table(name = "board")

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Board implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("columnType ASC")
    private SortedSet<ColumnDefinition> columnDefinitions;

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

    public SortedSet<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(SortedSet<ColumnDefinition> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
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
            ", informationCards=" + informationCards +
            '}';
    }
}
