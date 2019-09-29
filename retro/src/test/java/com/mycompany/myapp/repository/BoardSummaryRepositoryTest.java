package com.mycompany.myapp.repository;

import com.mycompany.myapp.RetroApp;
import com.mycompany.myapp.domain.BoardColumn;
import com.mycompany.myapp.domain.BoardSummary;
import com.mycompany.myapp.service.mapper.BoardSummaryMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = RetroApp.class)
public class BoardSummaryRepositoryTest {

    @Autowired
    private BoardSummaryRepository boardSummaryRepository;

    private BoardSummary boardSummary;
    private BoardColumn boardColumn;

    @Autowired
    private BoardSummaryMapper boardSummaryMapper;

    @BeforeEach
    public void setup() {
        boardColumn = new BoardColumn().color(1).columnId(1).value("Test");
        boardSummary = new BoardSummary()
            .boardId(UUID.randomUUID()).boardName("test")
            .archived(true).dateCreated(LocalDate.MAX).addBoardColumns(boardColumn);
    }


    @Test
    public void testFetchTypeEagerBoardSummary() {
        boardSummaryRepository.save(boardSummary);
        List<BoardSummary> boardSummaries = boardSummaryRepository.findAll();
        Assertions.assertThat(boardSummaries).isNotEmpty();
        BoardSummary boardSummary = boardSummaries.get(0);
        Assertions.assertThat(boardSummary.getBoardColumns()).isNotEmpty();

        BoardColumn boardColumn = boardSummary.getBoardColumns().iterator().next();
        Assertions.assertThat(boardColumn.getId()).isNotNull();
        Assertions.assertThat(boardColumn.getColor()).isEqualTo(this.boardColumn.getColor());
        Assertions.assertThat(boardColumn.getColumnId()).isEqualTo(this.boardColumn.getColumnId());
        Assertions.assertThat(boardColumn.getValue()).isEqualTo(this.boardColumn.getValue());

    }
}
