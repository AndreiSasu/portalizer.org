package org.portalizer.demodata;

import org.portalizer.demodata.steps.*;
import org.portalizer.domain.Board;

public class BoardBuilder {

    final Board board;
    public BoardBuilder() {
        this.board = new Board();
    }

    public BoardBuilder withCreatedAtStep(final DateStep dateStep) {
        board.setCreatedAt(dateStep.apply());
        return this;
    }

    public BoardBuilder withNameStep(final NameStep nameStep) {
        board.setName(nameStep.apply());
        return this;
    }

    public BoardBuilder withDescriptionStep(final DescriptionStep descriptionStep) {
        board.setDescription(descriptionStep.apply());
        return this;
    }

    public BoardBuilder withColumnsStep(final ColumnsStep columnsStep) {
        columnsStep.apply(board);
        return this;
    }

    public BoardBuilder withCardsStep(final CardsStep cardsStep) {
        cardsStep.apply(board);
        return this;
    }

    public Board build() {
        return board;
    }
}
