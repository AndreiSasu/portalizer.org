package org.portalizer.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.portalizer.PortalizerApp;
import org.portalizer.domain.Board;
import org.portalizer.repository.BoardRepository;
import org.portalizer.service.BoardService;
import org.portalizer.service.dto.BoardSummaryDTO;
import org.portalizer.service.dto.UpdateBoardDTO;
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
}
