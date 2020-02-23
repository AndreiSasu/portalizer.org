package org.portalizer.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.portalizer.PortalizerApp;
import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.repository.BoardRepository;
import org.portalizer.service.BoardService;
import org.portalizer.service.dto.*;
import org.portalizer.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

@SpringBootTest(classes = PortalizerApp.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardServiceImplTest {

    private Board savedBoard;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @BeforeAll
    @Commit
    public void setup() {
        boardRepository.deleteAll();
        savedBoard = boardRepository.save(EntityUtils.validBoard());
    }

    @Test
    public void testBoardSummaryDtoHasCorrectCount() {
        final List<BoardSummaryDTO> boardSummaryDTOS = boardService.findAll();
        Assertions.assertThat(boardSummaryDTOS).hasSize(1).allSatisfy(boardSummaryDTO -> {
            Assertions.assertThat(boardSummaryDTO.getTotalCards()).isEqualTo(3);
        });
    }

    @Test
    public void testUpdateBoardTransactionCommitted() {
        final Board board = boardRepository.findById(savedBoard.getId()).get();
        final String previousName = board.getName();
        final UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setId(board.getId());
        updateBoardDTO.setName(board.getName() + "updated");
        boardService.update(board.getId(), updateBoardDTO);
        final Board updatedBoard = boardRepository.findById(savedBoard.getId()).get();
        Assertions.assertThat(updatedBoard.getName()).isEqualTo(board.getName()+"updated");
    }

    @Test
    public void testReorderColumns() {
        final List<ColumnDefinition> columnDefinitions = savedBoard.getColumnDefinitions();
        final ColumnDefinition zero = columnDefinitions.get(0);
        final ColumnDefinition two = columnDefinitions.get(2);
        final ReorderColumnsDTO reorderColumnsDTO = new ReorderColumnsDTO();
        reorderColumnsDTO.setBoardId(savedBoard.getId());
        reorderColumnsDTO.setOldIndex(2);
        reorderColumnsDTO.setNewIndex(0);
        final BoardDTO boardDTO = boardService.reorderColumns(savedBoard.getId(), reorderColumnsDTO);
        Assertions.assertThat(boardDTO).isNotNull();
        final List<ColumnDefinitionDTO> columnDefinitionDTOS = boardDTO.getColumnDefinitions();
        Assertions.assertThat(columnDefinitionDTOS.get(0).getKey()).isEqualTo(two.getKey());
        Assertions.assertThat(columnDefinitionDTOS.get(0).getTitle()).isEqualTo(two.getTitle());
        Assertions.assertThat(columnDefinitionDTOS.get(0).getPriority()).isEqualTo(two.getPriority());
    }
}
