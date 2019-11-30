package org.portalizer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.portalizer.PortalizerApp;
import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.domain.ColumnType;
import org.portalizer.domain.InformationCard;
import org.portalizer.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.SortedSet;

@SpringBootTest(classes = PortalizerApp.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InformationCardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private InformationCardRepository informationCardRepository;

    private Board savedBoard;

    @BeforeAll
    public void setup() {
        SortedSet<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
        Board board = new Board();
        List<InformationCard> informationCards = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setColumnDefinitions(columnDefinitions);
        board.setInformationCards(informationCards);
        board.setName("Test");
        savedBoard = boardRepository.save(board);
    }

    @Test
    public void testCanRemoveCardFromExistingBoard() {
        final Board fullBoardBeforeRemove = boardRepository.findFullBoardById(savedBoard.getId()).get();
        int sizeBefore = fullBoardBeforeRemove.getInformationCards().size();
        final InformationCard toRemove = fullBoardBeforeRemove.getInformationCards().get(0);

        informationCardRepository.delete(toRemove);

        final Board fullBoardAfterRemove = boardRepository.findFullBoardById(savedBoard.getId()).get();
        int sizeAfter = fullBoardAfterRemove.getInformationCards().size();
        Assertions.assertThat(sizeAfter).isEqualTo(sizeBefore - 1);

    }

    @Test
    public void testCannotAddCardWithoutBoardReference() {
        InformationCard informationCard = new InformationCard();
        informationCard.setColumnType(ColumnType.GLAD);
        informationCard.setText("Some text");
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardRepository.save(informationCard));
        Assertions.assertThat(throwable.getCause().getCause()).isInstanceOf(javax.validation.ConstraintViolationException.class);
    }

    @Test
    public void testCannotAddCardWithoutColumnType() {
        InformationCard informationCard = new InformationCard();
        informationCard.setText("Some text");
        informationCard.setBoard(savedBoard);
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardRepository.save(informationCard));
        Assertions.assertThat(throwable.getCause().getCause()).isInstanceOf(javax.validation.ConstraintViolationException.class);
    }

    @Test
    public void testAddCardToExistingBoardAutoGeneratesFields() {
        InformationCard informationCard = new InformationCard();
        informationCard.setColumnType(ColumnType.GLAD);
        informationCard.setText("Some text 123");
        informationCard.setBoard(savedBoard);
        InformationCard savedCard = informationCardRepository.save(informationCard);
        Assertions.assertThat(savedCard.getId()).isNotNull();
        Assertions.assertThat(savedCard.getCreatedAt()).isNotNull();
    }
}
