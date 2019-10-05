package org.portalizer.retro.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.portalizer.retro.RetroApp;
import org.portalizer.retro.domain.Board;
import org.portalizer.retro.domain.ColumnDefinition;
import org.portalizer.retro.domain.ColumnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.SortedSet;
import java.util.TreeSet;

@SpringBootTest(classes = RetroApp.class)
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;


    @Test
//    @Transactional
    public void testBoardIdLazyLoading() {
        Board board = Board.builder().build();
        SortedSet<ColumnDefinition> columnDefinitions = buildColumnDefinitions();
        board.setColumnDefinitions(columnDefinitions);

        Board savedBoard = boardRepository.save(board);
        Board lazyBoard = boardRepository.findById(savedBoard.getId()).get();

        Assertions.assertThat(lazyBoard.getId()).isEqualByComparingTo(savedBoard.getId());


        Board fullBoard = boardRepository.findFullBoardById(savedBoard.getId()).get();
        Assertions.assertThat(fullBoard.getColumnDefinitions()).isEqualTo(columnDefinitions);
    }

    private SortedSet<ColumnDefinition> buildColumnDefinitions() {
        ColumnDefinition mad = new ColumnDefinition();
        mad.setColumnType(ColumnType.MAD);
        mad.setTitle("What Makes me Mad");

        ColumnDefinition sad = new ColumnDefinition();
        sad.setColumnType(ColumnType.SAD);
        sad.setTitle("What Makes me Mad");

        ColumnDefinition glad = new ColumnDefinition();
        glad.setColumnType(ColumnType.GLAD);
        glad.setTitle("What Makes me Glad");

        SortedSet<ColumnDefinition> columnDefinitions = new TreeSet<>();
        columnDefinitions.add(mad);
        columnDefinitions.add(sad);
        columnDefinitions.add(glad);
        return columnDefinitions;
    }
}
