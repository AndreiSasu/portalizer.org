package org.portalizer.demodata;

import org.portalizer.demodata.steps.CardsStep;
import org.portalizer.demodata.steps.ColumnsStep;
import org.portalizer.demodata.steps.DateStep;
import org.portalizer.demodata.steps.DescriptionStep;
import org.portalizer.demodata.steps.NameStep;
import org.portalizer.demodata.steps.OwnerStep;
import org.portalizer.domain.Board;
import org.portalizer.repository.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Profile("demo")
@Order(1)
public class SetupDemoBoards implements ApplicationRunner {

    private final BoardRepository boardRepository;
    private final NameStep nameStep;
    private final DescriptionStep descriptionStep;
    private final ColumnsStep columnsStep;
    private final CardsStep cardsStep;
    private final DateStep dateStep;
    private final OwnerStep ownerStep;
    private final int maxBoards;

    private static final Logger logger = LoggerFactory.getLogger(SetupDemoBoards.class);

    public SetupDemoBoards(@Value("${portalizer.demo.max-boards}") final int maxBoards,
                           final BoardRepository boardRepository,
                           final NameStep nameStep,
                           final DescriptionStep descriptionStep,
                           final ColumnsStep columnsStep,
                           final CardsStep cardsStep,
                           final DateStep dateStep,
                           final OwnerStep ownerStep) {
        this.boardRepository = boardRepository;
        this.maxBoards = maxBoards;
        this.nameStep = nameStep;
        this.descriptionStep = descriptionStep;
        this.columnsStep = columnsStep;
        this.cardsStep = cardsStep;
        this.dateStep = dateStep;
        this.ownerStep = ownerStep;
    }

    @Override
    public void run(ApplicationArguments args) {

        logger.info("Adding {} demo boards.", maxBoards);

        for (int i = 0; i < maxBoards; i++) {

            Board board = new BoardBuilder()
                .withNameStep(nameStep)
                .withDescriptionStep(descriptionStep)
                .withCreatedAtStep(dateStep)
                .withColumnsStep(columnsStep)
                .withCardsStep(cardsStep)
                .withOwnerStep(ownerStep)
                .build();
            boardRepository.save(board);

        }

        logger.info("Added {} demo boards. Total {} boards.", maxBoards, boardRepository.count());
    }
}
