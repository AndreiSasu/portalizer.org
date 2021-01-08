package org.portalizer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.portalizer.PortalizerApp;
import org.portalizer.demodata.UserFactory;
import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.domain.InformationCard;
import org.portalizer.domain.User;
import org.portalizer.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


@SpringBootTest(classes = PortalizerApp.class)
public class BoardRepositoryTest {

    private BoardRepository boardRepository;
    private InformationCardRepository informationCardRepository;
    private UserFactory userFactory;

    @Autowired
    public BoardRepositoryTest(BoardRepository boardRepository, InformationCardRepository informationCardRepository, UserFactory userFactory) {
        this.boardRepository = boardRepository;
        this.informationCardRepository = informationCardRepository;
        this.userFactory = userFactory;
    }

    @Test
    public void testColumnOrderingPreserved() {
        Board board = new Board();
        board.setName("Test");

        List<ColumnDefinition> columnDefinitions = new ArrayList<>();

        ColumnDefinition keep = new ColumnDefinition();
        keep.setKey(UUID.randomUUID());
        keep.setTitle("Keep");
        keep.setBoard(board);

        ColumnDefinition add = new ColumnDefinition();
        add.setKey(UUID.randomUUID());
        add.setTitle("Add");
        add.setBoard(board);

        ColumnDefinition less = new ColumnDefinition();
        less.setKey(UUID.randomUUID());
        less.setTitle("Less");
        less.setBoard(board);

        ColumnDefinition more = new ColumnDefinition();
        more.setKey(UUID.randomUUID());
        more.setTitle("More");
        more.setBoard(board);

        columnDefinitions.addAll(Arrays.asList(keep, add, less, more));
        board.setColumnDefinitions(columnDefinitions);

        Board savedBoard = boardRepository.save(board);
        Board lazyBoard = boardRepository.findById(savedBoard.getId()).get();

        final List<ColumnDefinition> savedColumnDefinitions = lazyBoard.getColumnDefinitions();
        for(int i = 0; i < savedColumnDefinitions.size(); i++) {
            Assertions.assertThat(savedColumnDefinitions.get(i)).isEqualToIgnoringGivenFields(columnDefinitions.get(i), "id", "board");
        }
    }

    @Test
    public void testBoardIdLazyLoading() {
        Board board = new Board();
        board.setName("Test");
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions(board);
        board.setColumnDefinitions(columnDefinitions);

        Board savedBoard = boardRepository.save(board);
        Board lazyBoard = boardRepository.findById(savedBoard.getId()).get();

        Assertions.assertThat(lazyBoard.getId()).isEqualByComparingTo(savedBoard.getId());


        Board fullBoard = boardRepository.findFullBoardById(savedBoard.getId()).get();
        Assertions.assertThat(fullBoard.getColumnDefinitions()).containsAll(columnDefinitions);
    }

    @Test
    public void testFindFullBoardByIdNullInformationCardsReturnsEmptyList() {
        Board board = new Board();
        board.setName("Test");
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions(board);
        board.setColumnDefinitions(columnDefinitions);

        Board savedBoard = boardRepository.save(board);
        Board foundBoard = boardRepository.findFullBoardById(savedBoard.getId()).get();
        Assertions.assertThat(foundBoard.getInformationCards()).isEmpty();
    }

    @Test
    public void testCanAddInformationCardToExistingBoard() {
        Board board = new Board();
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions(board);
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
        newCard.setColumnKey(UUID.randomUUID());
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


    @Test
    public void testBoardOwnerEagerlyFetched() {
        final User user = userFactory.newUser();
        final Board board = EntityUtils.validBoard();
        board.setOwner(user);
        boardRepository.save(board);

        final Board foundBoard = boardRepository.findById(board.getId()).orElseThrow(() -> new IllegalStateException("Board not found!"));
        Assertions.assertThat(foundBoard.getOwner()).isEqualTo(user);
    }

    @Test
    public void testFindBoardsByOwnerId() {
        final User user = userFactory.newUser();
        final Board board = EntityUtils.validBoard();
        board.setOwner(user);
        boardRepository.save(board);
        boardRepository.save(EntityUtils.validBoard());
        final List<Board> boards = boardRepository.findBoardsByOwnerId(user.getId());
        Assertions.assertThat(boards).allSatisfy(b -> {
            Assertions.assertThat(b.getId()).isEqualTo(board.getId());
        });
    }

    @Test
    public void testColumnKeySavedWhenPersistingColumnDefinition() {
        final Board board = new Board();
        board.setName("test name");
        final ColumnDefinition columnDefinition = new ColumnDefinition();
        columnDefinition.setTitle("test title");
        columnDefinition.setBoard(board);
        board.setColumnDefinitions(List.of(columnDefinition));
        final UUID boardId = this.boardRepository.save(board).getId();

        this.boardRepository.findFullBoardById(boardId).get().getColumnDefinitions().forEach(savedColumnDefinition -> {
            Assertions.assertThat(savedColumnDefinition.getKey()).isNotNull();
        });
    }


}
