package org.portalizer.retro.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.portalizer.retro.RetroApp;
import org.portalizer.retro.domain.Board;
import org.portalizer.retro.domain.ColumnDefinition;
import org.portalizer.retro.domain.InformationCard;
import org.portalizer.retro.repository.InformationCardRepository;
import org.portalizer.retro.service.InformationCardService;
import org.portalizer.retro.service.dto.BoardDTO;
import org.portalizer.retro.service.mapper.BoardMapper;
import org.portalizer.retro.utils.EntityUtils;
import org.portalizer.retro.web.rest.errors.ExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.SortedSet;

import static org.portalizer.retro.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RetroApp.class)
public class InformationCardResourceIT {

    @Autowired
    private InformationCardService informationCardService;

    @Autowired
    private InformationCardRepository boardRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    @Autowired
    private BoardMapper boardMapper;

    private MockMvc boardResourceMockMVC;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InformationCardResource informationCardResource = new InformationCardResource(informationCardService);
        this.boardResourceMockMVC = MockMvcBuilders.standaloneSetup(informationCardResource)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @Test
    public void testAddCard() throws Exception {
        SortedSet<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
        Board board = new Board();
        board.setColumnDefinitions(columnDefinitions);
        board.setName("Test");

        BoardDTO boardDTO = boardMapper.toDto(board);

        boardResourceMockMVC.perform(post("/api/boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardDTO)))
            .andExpect(status().isCreated());

//        List<Board> allBoards = boardRepository.findAll();
//        Assertions.assertThat(allBoards).hasSize(1);
//
//        final Board savedBoard = allBoards.get(0);
//
//        Assertions.assertThat(savedBoard.getId()).isNotNull();
//        Assertions.assertThat(savedBoard.getName()).isEqualTo("Test");
//        Assertions.assertThat(savedBoard.getCreatedAt()).isNotNull();
//        Assertions.assertThat(savedBoard.getColumnDefinitions()).isEqualTo(columnDefinitions);

    }

    @Test
    public void testDeleteBoard() throws Exception {

        SortedSet<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
        Board board = new Board();
        List<InformationCard> informationCard = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setInformationCards(informationCard);
        board.setColumnDefinitions(columnDefinitions);
        board.setName("Test");

//        final Board savedBoard = boardRepository.save(board);
//        boardResourceMockMVC.perform(delete("/api/boards/{id}", savedBoard.getId())
//                .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//        final List<Board> boards = boardRepository.findAll();
//        Assertions.assertThat(boards).isEmpty();

    }

}
