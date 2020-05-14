package org.portalizer.demodata;

import org.portalizer.demodata.steps.*;
import org.portalizer.domain.Board;
import org.portalizer.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata")
public class SetupDemoBoards implements ApplicationRunner {

    private final BoardRepository boardRepository;
    private final NameStep nameStep;
    private final DescriptionStep descriptionStep;
    private final ColumnsStep columnsStep;
    private final CardsStep cardsStep;
    private final DateStep dateStep;
    private final int maxBoards;

    public SetupDemoBoards(@Value("${portalizer.demo.max-boards}") final int maxBoards,
                           final BoardRepository boardRepository,
                           final NameStep nameStep,
                           final DescriptionStep descriptionStep,
                           final ColumnsStep columnsStep,
                           final CardsStep cardsStep,
                           final DateStep dateStep) {
        this.boardRepository = boardRepository;
        this.maxBoards = maxBoards;
        this.nameStep = nameStep;
        this.descriptionStep = descriptionStep;
        this.columnsStep = columnsStep;
        this.cardsStep = cardsStep;
        this.dateStep = dateStep;
    }

    @Override
    public void run(ApplicationArguments args) {

        for (int i = 0; i < maxBoards; i++) {

            Board board = new BoardBuilder()
                .withNameStep(nameStep)
                .withDescriptionStep(descriptionStep)
                .withColumnsStep(columnsStep)
                .withCardsStep(cardsStep)
                .build();
            boardRepository.save(board);

        }
    }
}
