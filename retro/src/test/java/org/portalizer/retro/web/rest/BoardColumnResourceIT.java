package org.portalizer.retro.web.rest;

import org.portalizer.retro.RetroApp;
import org.portalizer.retro.domain.BoardColumn;
import org.portalizer.retro.repository.BoardColumnRepository;
import org.portalizer.retro.service.BoardColumnService;
import org.portalizer.retro.service.dto.BoardColumnDTO;
import org.portalizer.retro.service.mapper.BoardColumnMapper;
import org.portalizer.retro.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static org.portalizer.retro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BoardColumnResource} REST controller.
 */
@SpringBootTest(classes = RetroApp.class)
public class BoardColumnResourceIT {

    private static final Integer DEFAULT_COLOR = 1;
    private static final Integer UPDATED_COLOR = 2;
    private static final Integer SMALLER_COLOR = 1 - 1;

    private static final Integer DEFAULT_COLUMN_ID = 4;
    private static final Integer UPDATED_COLUMN_ID = 3;
    private static final Integer SMALLER_COLUMN_ID = 4 - 1;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    @Autowired
    private BoardColumnMapper boardColumnMapper;

    @Autowired
    private BoardColumnService boardColumnService;

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

    private MockMvc restBoardColumnMockMvc;

    private BoardColumn boardColumn;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BoardColumnResource boardColumnResource = new BoardColumnResource(boardColumnService);
        this.restBoardColumnMockMvc = MockMvcBuilders.standaloneSetup(boardColumnResource)
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
    public static BoardColumn createEntity(EntityManager em) {
        BoardColumn boardColumn = new BoardColumn()
            .color(DEFAULT_COLOR)
            .columnId(DEFAULT_COLUMN_ID)
            .value(DEFAULT_VALUE);
        return boardColumn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoardColumn createUpdatedEntity(EntityManager em) {
        BoardColumn boardColumn = new BoardColumn()
            .color(UPDATED_COLOR)
            .columnId(UPDATED_COLUMN_ID)
            .value(UPDATED_VALUE);
        return boardColumn;
    }

    @BeforeEach
    public void initTest() {
        boardColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoardColumn() throws Exception {
        int databaseSizeBeforeCreate = boardColumnRepository.findAll().size();

        // Create the BoardColumn
        BoardColumnDTO boardColumnDTO = boardColumnMapper.toDto(boardColumn);
        restBoardColumnMockMvc.perform(post("/api/board-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardColumnDTO)))
            .andExpect(status().isCreated());

        // Validate the BoardColumn in the database
        List<BoardColumn> boardColumnList = boardColumnRepository.findAll();
        assertThat(boardColumnList).hasSize(databaseSizeBeforeCreate + 1);
        BoardColumn testBoardColumn = boardColumnList.get(boardColumnList.size() - 1);
        assertThat(testBoardColumn.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testBoardColumn.getColumnId()).isEqualTo(DEFAULT_COLUMN_ID);
        assertThat(testBoardColumn.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createBoardColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boardColumnRepository.findAll().size();

        // Create the BoardColumn with an existing ID
        boardColumn.setId(1L);
        BoardColumnDTO boardColumnDTO = boardColumnMapper.toDto(boardColumn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoardColumnMockMvc.perform(post("/api/board-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoardColumn in the database
        List<BoardColumn> boardColumnList = boardColumnRepository.findAll();
        assertThat(boardColumnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkColumnIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = boardColumnRepository.findAll().size();
        // set the field null
        boardColumn.setColumnId(null);

        // Create the BoardColumn, which fails.
        BoardColumnDTO boardColumnDTO = boardColumnMapper.toDto(boardColumn);

        restBoardColumnMockMvc.perform(post("/api/board-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardColumnDTO)))
            .andExpect(status().isBadRequest());

        List<BoardColumn> boardColumnList = boardColumnRepository.findAll();
        assertThat(boardColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = boardColumnRepository.findAll().size();
        // set the field null
        boardColumn.setValue(null);

        // Create the BoardColumn, which fails.
        BoardColumnDTO boardColumnDTO = boardColumnMapper.toDto(boardColumn);

        restBoardColumnMockMvc.perform(post("/api/board-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardColumnDTO)))
            .andExpect(status().isBadRequest());

        List<BoardColumn> boardColumnList = boardColumnRepository.findAll();
        assertThat(boardColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoardColumns() throws Exception {
        // Initialize the database
        boardColumnRepository.saveAndFlush(boardColumn);

        // Get all the boardColumnList
        restBoardColumnMockMvc.perform(get("/api/board-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].columnId").value(hasItem(DEFAULT_COLUMN_ID)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getBoardColumn() throws Exception {
        // Initialize the database
        boardColumnRepository.saveAndFlush(boardColumn);

        // Get the boardColumn
        restBoardColumnMockMvc.perform(get("/api/board-columns/{id}", boardColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(boardColumn.getId().intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.columnId").value(DEFAULT_COLUMN_ID))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoardColumn() throws Exception {
        // Get the boardColumn
        restBoardColumnMockMvc.perform(get("/api/board-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoardColumn() throws Exception {
        // Initialize the database
        boardColumnRepository.saveAndFlush(boardColumn);

        int databaseSizeBeforeUpdate = boardColumnRepository.findAll().size();

        // Update the boardColumn
        BoardColumn updatedBoardColumn = boardColumnRepository.findById(boardColumn.getId()).get();
        // Disconnect from session so that the updates on updatedBoardColumn are not directly saved in db
        em.detach(updatedBoardColumn);
        updatedBoardColumn
            .color(UPDATED_COLOR)
            .columnId(UPDATED_COLUMN_ID)
            .value(UPDATED_VALUE);
        BoardColumnDTO boardColumnDTO = boardColumnMapper.toDto(updatedBoardColumn);

        restBoardColumnMockMvc.perform(put("/api/board-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardColumnDTO)))
            .andExpect(status().isOk());

        // Validate the BoardColumn in the database
        List<BoardColumn> boardColumnList = boardColumnRepository.findAll();
        assertThat(boardColumnList).hasSize(databaseSizeBeforeUpdate);
        BoardColumn testBoardColumn = boardColumnList.get(boardColumnList.size() - 1);
        assertThat(testBoardColumn.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testBoardColumn.getColumnId()).isEqualTo(UPDATED_COLUMN_ID);
        assertThat(testBoardColumn.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingBoardColumn() throws Exception {
        int databaseSizeBeforeUpdate = boardColumnRepository.findAll().size();

        // Create the BoardColumn
        BoardColumnDTO boardColumnDTO = boardColumnMapper.toDto(boardColumn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoardColumnMockMvc.perform(put("/api/board-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoardColumn in the database
        List<BoardColumn> boardColumnList = boardColumnRepository.findAll();
        assertThat(boardColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoardColumn() throws Exception {
        // Initialize the database
        boardColumnRepository.saveAndFlush(boardColumn);

        int databaseSizeBeforeDelete = boardColumnRepository.findAll().size();

        // Delete the boardColumn
        restBoardColumnMockMvc.perform(delete("/api/board-columns/{id}", boardColumn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoardColumn> boardColumnList = boardColumnRepository.findAll();
        assertThat(boardColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoardColumn.class);
        BoardColumn boardColumn1 = new BoardColumn();
        boardColumn1.setId(1L);
        BoardColumn boardColumn2 = new BoardColumn();
        boardColumn2.setId(boardColumn1.getId());
        assertThat(boardColumn1).isEqualTo(boardColumn2);
        boardColumn2.setId(2L);
        assertThat(boardColumn1).isNotEqualTo(boardColumn2);
        boardColumn1.setId(null);
        assertThat(boardColumn1).isNotEqualTo(boardColumn2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoardColumnDTO.class);
        BoardColumnDTO boardColumnDTO1 = new BoardColumnDTO();
        boardColumnDTO1.setId(1L);
        BoardColumnDTO boardColumnDTO2 = new BoardColumnDTO();
        assertThat(boardColumnDTO1).isNotEqualTo(boardColumnDTO2);
        boardColumnDTO2.setId(boardColumnDTO1.getId());
        assertThat(boardColumnDTO1).isEqualTo(boardColumnDTO2);
        boardColumnDTO2.setId(2L);
        assertThat(boardColumnDTO1).isNotEqualTo(boardColumnDTO2);
        boardColumnDTO1.setId(null);
        assertThat(boardColumnDTO1).isNotEqualTo(boardColumnDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(boardColumnMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(boardColumnMapper.fromId(null)).isNull();
    }
}
