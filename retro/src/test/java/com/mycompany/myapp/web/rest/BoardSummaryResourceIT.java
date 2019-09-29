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
    public void createBoardSummary() throws Exception {
        int databaseSizeBeforeCreate = boardSummaryRepository.findAll().size();

        // Create the BoardSummary
        BoardSummaryDTO boardSummaryDTO = boardSummaryMapper.toDto(boardSummary);
        restBoardSummaryMockMvc.perform(post("/api/board-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardSummaryDTO)))
            .andExpect(status().isCreated());

        // Validate the BoardSummary in the database
        List<BoardSummary> boardSummaryList = boardSummaryRepository.findAll();
        assertThat(boardSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        BoardSummary testBoardSummary = boardSummaryList.get(boardSummaryList.size() - 1);
        assertThat(testBoardSummary.isArchived()).isEqualTo(DEFAULT_ARCHIVED);
        assertThat(testBoardSummary.getBoardName()).isEqualTo(DEFAULT_BOARD_NAME);
        assertThat(testBoardSummary.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testBoardSummary.getBoardId()).isEqualTo(DEFAULT_BOARD_ID);
    }

    @Test
    @Transactional
    public void createBoardSummaryWithBoardColumn() throws Exception {
        int databaseSizeBeforeCreate = boardSummaryRepository.findAll().size();

        // Create the BoardSummary
        BoardColumn expectedBoardColumn = new BoardColumn().color(1).columnId(1).value("Test");
        BoardSummary boardSummary = createEntity(em).addBoardColumns(expectedBoardColumn);
        BoardSummaryDTO boardSummaryDTO = boardSummaryMapper.toDto(boardSummary);
        restBoardSummaryMockMvc.perform(post("/api/board-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardSummaryDTO)))
            .andExpect(status().isCreated());

        // Validate the BoardSummary in the database
        List<BoardSummary> boardSummaryList = boardSummaryRepository.findAll();
        assertThat(boardSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        BoardSummary testBoardSummary = boardSummaryList.get(boardSummaryList.size() - 1);
        assertThat(testBoardSummary.isArchived()).isEqualTo(DEFAULT_ARCHIVED);
        assertThat(testBoardSummary.getBoardName()).isEqualTo(DEFAULT_BOARD_NAME);
        assertThat(testBoardSummary.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testBoardSummary.getBoardId()).isEqualTo(DEFAULT_BOARD_ID);

        BoardColumn boardColumn = testBoardSummary.getBoardColumns().iterator().next();
        Assertions.assertThat(boardColumn.getId()).isNotNull();
        Assertions.assertThat(boardColumn.getColor()).isEqualTo(expectedBoardColumn.getColor());
        Assertions.assertThat(boardColumn.getColumnId()).isEqualTo(expectedBoardColumn.getColumnId());
        Assertions.assertThat(boardColumn.getValue()).isEqualTo(expectedBoardColumn.getValue());
    }

    @Test
    @Transactional
    public void createBoardSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boardSummaryRepository.findAll().size();

        // Create the BoardSummary with an existing ID
        boardSummary.setId(1L);
        BoardSummaryDTO boardSummaryDTO = boardSummaryMapper.toDto(boardSummary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoardSummaryMockMvc.perform(post("/api/board-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardSummaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoardSummary in the database
        List<BoardSummary> boardSummaryList = boardSummaryRepository.findAll();
        assertThat(boardSummaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBoardNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = boardSummaryRepository.findAll().size();
        // set the field null
        boardSummary.setBoardName(null);

        // Create the BoardSummary, which fails.
        BoardSummaryDTO boardSummaryDTO = boardSummaryMapper.toDto(boardSummary);

        restBoardSummaryMockMvc.perform(post("/api/board-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardSummaryDTO)))
            .andExpect(status().isBadRequest());

        List<BoardSummary> boardSummaryList = boardSummaryRepository.findAll();
        assertThat(boardSummaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoardIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = boardSummaryRepository.findAll().size();
        // set the field null
        boardSummary.setBoardId(null);

        // Create the BoardSummary, which fails.
        BoardSummaryDTO boardSummaryDTO = boardSummaryMapper.toDto(boardSummary);

        restBoardSummaryMockMvc.perform(post("/api/board-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardSummaryDTO)))
            .andExpect(status().isBadRequest());

        List<BoardSummary> boardSummaryList = boardSummaryRepository.findAll();
        assertThat(boardSummaryList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].id").value(hasItem(boardSummary.getId().intValue())))
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
        restBoardSummaryMockMvc.perform(get("/api/board-summaries/{id}", boardSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(boardSummary.getId().intValue()))
            .andExpect(jsonPath("$.archived").value(DEFAULT_ARCHIVED.booleanValue()))
            .andExpect(jsonPath("$.boardName").value(DEFAULT_BOARD_NAME.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.boardId").value(DEFAULT_BOARD_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoardSummary() throws Exception {
        // Get the boardSummary
        restBoardSummaryMockMvc.perform(get("/api/board-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoardSummary() throws Exception {
        // Initialize the database
        boardSummaryRepository.saveAndFlush(boardSummary);

        int databaseSizeBeforeUpdate = boardSummaryRepository.findAll().size();

        // Update the boardSummary
        BoardSummary updatedBoardSummary = boardSummaryRepository.findById(boardSummary.getId()).get();
        // Disconnect from session so that the updates on updatedBoardSummary are not directly saved in db
        em.detach(updatedBoardSummary);
        updatedBoardSummary
            .archived(UPDATED_ARCHIVED)
            .boardName(UPDATED_BOARD_NAME)
            .dateCreated(UPDATED_DATE_CREATED)
            .boardId(UPDATED_BOARD_ID);
        BoardSummaryDTO boardSummaryDTO = boardSummaryMapper.toDto(updatedBoardSummary);

        restBoardSummaryMockMvc.perform(put("/api/board-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardSummaryDTO)))
            .andExpect(status().isOk());

        // Validate the BoardSummary in the database
        List<BoardSummary> boardSummaryList = boardSummaryRepository.findAll();
        assertThat(boardSummaryList).hasSize(databaseSizeBeforeUpdate);
        BoardSummary testBoardSummary = boardSummaryList.get(boardSummaryList.size() - 1);
        assertThat(testBoardSummary.isArchived()).isEqualTo(UPDATED_ARCHIVED);
        assertThat(testBoardSummary.getBoardName()).isEqualTo(UPDATED_BOARD_NAME);
        assertThat(testBoardSummary.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testBoardSummary.getBoardId()).isEqualTo(UPDATED_BOARD_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBoardSummary() throws Exception {
        int databaseSizeBeforeUpdate = boardSummaryRepository.findAll().size();

        // Create the BoardSummary
        BoardSummaryDTO boardSummaryDTO = boardSummaryMapper.toDto(boardSummary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoardSummaryMockMvc.perform(put("/api/board-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardSummaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoardSummary in the database
        List<BoardSummary> boardSummaryList = boardSummaryRepository.findAll();
        assertThat(boardSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoardSummary() throws Exception {
        // Initialize the database
        boardSummaryRepository.saveAndFlush(boardSummary);

        int databaseSizeBeforeDelete = boardSummaryRepository.findAll().size();

        // Delete the boardSummary
        restBoardSummaryMockMvc.perform(delete("/api/board-summaries/{id}", boardSummary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoardSummary> boardSummaryList = boardSummaryRepository.findAll();
        assertThat(boardSummaryList).hasSize(databaseSizeBeforeDelete - 1);
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
        BoardSummaryDTO boardSummaryDTO1 = new BoardSummaryDTO();
        boardSummaryDTO1.setId(1L);
        BoardSummaryDTO boardSummaryDTO2 = new BoardSummaryDTO();
        assertThat(boardSummaryDTO1).isNotEqualTo(boardSummaryDTO2);
        boardSummaryDTO2.setId(boardSummaryDTO1.getId());
        assertThat(boardSummaryDTO1).isEqualTo(boardSummaryDTO2);
        boardSummaryDTO2.setId(2L);
        assertThat(boardSummaryDTO1).isNotEqualTo(boardSummaryDTO2);
        boardSummaryDTO1.setId(null);
        assertThat(boardSummaryDTO1).isNotEqualTo(boardSummaryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(boardSummaryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(boardSummaryMapper.fromId(null)).isNull();
    }
}
