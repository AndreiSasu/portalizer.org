package org.portalizer.retro.web.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.portalizer.retro.RetroApp;

import org.portalizer.retro.domain.Board;
import org.portalizer.retro.domain.ColumnDefinition;
import org.portalizer.retro.domain.InformationCard;
import org.portalizer.retro.repository.BoardRepository;
import org.portalizer.retro.service.BoardService;
import org.portalizer.retro.utils.EntityUtils;
import org.portalizer.retro.web.rest.errors.ExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.SortedSet;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.portalizer.retro.web.rest.TestUtil.createFormattingConversionService;
import static org.portalizer.retro.web.rest.TestUtil.findAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = RetroApp.class)
public class BoardResourceIT {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc boardResourceMockMVC;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BoardResource boardResource = new BoardResource(boardService);
        this.boardResourceMockMVC = MockMvcBuilders.standaloneSetup(boardResource)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @Test
    public void testAllBoardsAreReturned() throws Exception {


        SortedSet<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
        Board board = new Board();
        List<InformationCard> informationCard = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setInformationCards(informationCard);
        board.setColumnDefinitions(columnDefinitions);
        board.setName("Test");
        boardRepository.save(board);


        boardResourceMockMVC.perform(get("/api/boards"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").isNotEmpty())
            .andExpect(jsonPath("$.[*].name").value(hasItem("Test")))
            .andExpect(jsonPath("$.[*].createdAt").isNotEmpty())
            .andExpect(jsonPath("$.[*].columnDefinitions").isNotEmpty())
            .andExpect(jsonPath("$.[*].informationCards").isEmpty());
    }

    @Test
    public void testFullBoardReturnedById() throws Exception {
        SortedSet<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
        Board board = new Board();
        List<InformationCard> informationCard = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setInformationCards(informationCard);
        board.setColumnDefinitions(columnDefinitions);
        board.setName("Test");
        final Board savedBoard = boardRepository.save(board);

        boardResourceMockMVC.perform(get("/api/boards/{id}", savedBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(savedBoard.getId().toString()))
            .andExpect(jsonPath("$.name").value("Test"))
            .andExpect(jsonPath("$.createdAt").isNotEmpty())
            .andExpect(jsonPath("$.columnDefinitions").isNotEmpty())
            .andExpect(jsonPath("$.informationCards").isNotEmpty());
    }

}
