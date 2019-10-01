package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RetroApp;
import com.mycompany.myapp.domain.BoardColumn;
import com.mycompany.myapp.domain.BoardSummary;
import com.mycompany.myapp.repository.BoardSummaryRepository;
import com.mycompany.myapp.service.BoardSummaryService;
import com.mycompany.myapp.service.dto.BoardSummaryDTO;
import com.mycompany.myapp.service.mapper.BoardSummaryMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BoardSummaryResource} REST controller.
 */
@SpringBootTest(classes = RetroApp.class)
public class BoardSummaryResourceIT {

    private static final Boolean DEFAULT_ARCHIVED = false;
    private static final Boolean UPDATED_ARCHIVED = true;

    private static final String DEFAULT_BOARD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BOARD_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_CREATED = LocalDate.ofEpochDay(-1L);

    private static final UUID DEFAULT_BOARD_ID = UUID.randomUUID();
    private static final UUID UPDATED_BOARD_ID = UUID.randomUUID();

    @Autowired
    private BoardSummaryRepository boardSummaryRepository;

    @Autowired
    private BoardSummaryMapper boardSummaryMapper;

    @Autowired
    private BoardSummaryService boardSummaryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBoardSummaryMockMvc;

    private BoardSummary boardSummary;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BoardSummaryResource boardSummaryResource = new BoardSummaryResource(boardSummaryService);
        this.restBoardSummaryMockMvc = MockMvcBuilders.standaloneSetup(boardSummaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoardSummary createEntity(EntityManager em) {
        BoardSummary boardSummary = new BoardSummary()
            .archived(DEFAULT_ARCHIVED)
            .boardName(DEFAULT_BOARD_NAME)
            .dateCreated(DEFAULT_DATE_CREATED)
            .boardId(DEFAULT_BOARD_ID);
        return boardSummary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoardSummary createUpdatedEntity(EntityManager em) {
        BoardSummary boardSummary = new BoardSummary()
            .archived(UPDATED_ARCHIVED)
            .boardName(UPDATED_BOARD_NAME)
            .dateCreated(UPDATED_DATE_CREATED)
            .boardId(UPDATED_BOARD_ID);
        return boardSummary;
    }

    @BeforeEach
    public void initTest() {
        boardSummary = createEntity(em);
    }


    @Test
    @Transactional
    public void getAllBoardSummaries() throws Exception {
        // Initialize the database
        boardSummaryRepository.saveAndFlush(boardSummary);

        // Get all the boardSummaryList
        restBoardSummaryMockMvc.perform(get("/api/board-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].archived").value(hasItem(DEFAULT_ARCHIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].boardName").value(hasItem(DEFAULT_BOARD_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].boardId").value(hasItem(DEFAULT_BOARD_ID.toString())));
    }

    @Test
    @Transactional
    public void getBoardSummary() throws Exception {
        // Initialize the database
        boardSummaryRepository.saveAndFlush(boardSummary);

        // Get the boardSummary
        restBoardSummaryMockMvc.perform(get("/api/board-summaries/{boardId}", boardSummary.getBoardId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.archived").value(DEFAULT_ARCHIVED.booleanValue()))
            .andExpect(jsonPath("$.boardName").value(DEFAULT_BOARD_NAME.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.boardId").value(DEFAULT_BOARD_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoardSummary() throws Exception {
        // Get the boardSummary
        restBoardSummaryMockMvc.perform(get("/api/board-summaries/{boardId}", UUID.randomUUID()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getInvalidBoardId() throws Exception {
        // Get the boardSummary
        restBoardSummaryMockMvc.perform(get("/api/board-summaries/{boardId}", Long.MAX_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoardSummary.class);
        BoardSummary boardSummary1 = new BoardSummary();
        boardSummary1.setId(1L);
        BoardSummary boardSummary2 = new BoardSummary();
        boardSummary2.setId(boardSummary1.getId());
        assertThat(boardSummary1).isEqualTo(boardSummary2);
        boardSummary2.setId(2L);
        assertThat(boardSummary1).isNotEqualTo(boardSummary2);
        boardSummary1.setId(null);
        assertThat(boardSummary1).isNotEqualTo(boardSummary2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoardSummaryDTO.class);
        UUID boardId1 = UUID.randomUUID();
        UUID boardId2 = UUID.randomUUID();
        BoardSummaryDTO boardSummaryDTO1 = new BoardSummaryDTO();
        boardSummaryDTO1.setBoardId(boardId1);
        BoardSummaryDTO boardSummaryDTO2 = new BoardSummaryDTO();
        assertThat(boardSummaryDTO1).isNotEqualTo(boardSummaryDTO2);
        boardSummaryDTO2.setBoardId(boardSummaryDTO1.getBoardId());
        assertThat(boardSummaryDTO1).isEqualTo(boardSummaryDTO2);
        boardSummaryDTO2.setBoardId(boardId2);
        assertThat(boardSummaryDTO1).isNotEqualTo(boardSummaryDTO2);
        boardSummaryDTO1.setBoardId(null);
        assertThat(boardSummaryDTO1).isNotEqualTo(boardSummaryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(boardSummaryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(boardSummaryMapper.fromId(null)).isNull();
    }
}
