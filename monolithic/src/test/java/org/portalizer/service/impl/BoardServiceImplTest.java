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
import org.portalizer.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void setup() {
        savedBoard = boardRepository.save(EntityUtils.validBoard());
    }

    @Test
    public void testBoardSummaryDtoHasCorrectCount() {
        final List<BoardSummaryDTO> boardSummaryDTOS = boardService.findAll();
        Assertions.assertThat(boardSummaryDTOS).hasSize(1).allSatisfy(boardSummaryDTO -> {
            Assertions.assertThat(boardSummaryDTO.getTotalCards()).isEqualTo(3);
        });
    }
}
