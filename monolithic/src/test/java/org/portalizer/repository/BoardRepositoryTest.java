package org.portalizer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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
import java.util.UUID;


@SpringBootTest(classes = PortalizerApp.class)
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private InformationCardRepository informationCardRepository;


    @Test
    public void testBoardIdLazyLoading() {
        Board board = new Board();
        board.setName("Test");
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
        board.setColumnDefinitions(columnDefinitions);

        Board savedBoard = boardRepository.save(board);
        Board lazyBoard = boardRepository.findById(savedBoard.getId()).get();

        Assertions.assertThat(lazyBoard.getId()).isEqualByComparingTo(savedBoard.getId());


        Board fullBoard = boardRepository.findFullBoardById(savedBoard.getId()).get();
        Assertions.assertThat(fullBoard.getColumnDefinitions()).isEqualTo(columnDefinitions);
    }

    @Test
    public void testFindFullBoardByIdNullInformationCardsReturnsEmptyList() {
        Board board = new Board();
        board.setName("Test");
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
        board.setColumnDefinitions(columnDefinitions);

        Board savedBoard = boardRepository.save(board);
        Board foundBoard = boardRepository.findFullBoardById(savedBoard.getId()).get();
        Assertions.assertThat(foundBoard.getInformationCards()).isEmpty();
    }

    @Test
    public void testCanAddInformationCardToExistingBoard() {
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
        Board board = new Board();
        List<InformationCard> informationCards = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setColumnDefinitions(columnDefinitions);
        board.setInformationCards(informationCards);
        board.setName("Test");
        Board savedBoard = boardRepository.save(board);
        final UUID key = savedBoard.getId();
        final List<InformationCard> foundInformationCards = informationCardRepository.findAllByBoardId(key).get();
        Assertions.assertThat(foundInformationCards).hasSize(columnDefinitions.size()).allMatch(informationCard ->
            savedBoard.getId().equals(informationCard.getBoard().getId()), ""
        );

        InformationCard newCard = new InformationCard();
        newCard.setText("Test Text");
        newCard.setColumnType(ColumnType.GLAD);
        newCard.setBoard(savedBoard);
        informationCardRepository.save(newCard);
        Assertions.assertThat(informationCardRepository.findAllByBoardId(savedBoard.getId()).get())
            .hasSize(foundInformationCards.size()+1)
            .allMatch(informationCard -> savedBoard.getId().equals(informationCard.getBoard().getId()), "");

        Board foundBoard = boardRepository.findFullBoardById(key).get();

        Assertions.assertThat(foundBoard.getInformationCards())
            .hasSize(foundInformationCards.size()+1).
            allMatch(informationCard -> savedBoard.getId().equals(informationCard.getBoard().getId()), "");

    }


}
