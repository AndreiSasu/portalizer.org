package org.portalizer.web.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.portalizer.PortalizerApp;

import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.domain.InformationCard;
import org.portalizer.repository.BoardRepository;
import org.portalizer.service.BoardService;
import org.portalizer.service.dto.BoardDTO;
import org.portalizer.service.mapper.BoardMapper;
import org.portalizer.utils.EntityUtils;
import org.portalizer.web.rest.errors.ExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.SortedSet;

import static org.hamcrest.Matchers.hasItem;

import static org.portalizer.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = PortalizerApp.class)
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

    @Autowired
    private BoardMapper boardMapper;

    private MockMvc boardResourceMockMVC;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BoardResource boardResource = new BoardResource(boardService);
        this.boardResourceMockMVC = MockMvcBuilders.standaloneSetup(boardResource)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @Test
    public void testBoardsAreReturnedPageableDefault() throws Exception {

        Board board = new Board();
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions(board);

        List<InformationCard> informationCard = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setInformationCards(informationCard);
        board.setColumnDefinitions(columnDefinitions);
        board.setName("Test");
        boardRepository.save(board);


        boardResourceMockMVC.perform(get("/api/retro/boards"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.content.[*].id").isNotEmpty())
            .andExpect(jsonPath("$.content.[*].name").value(hasItem("Test")))
            .andExpect(jsonPath("$.content.[*].createdAt").isNotEmpty())
            .andExpect(jsonPath("$.content.[*].columnDefinitions").isNotEmpty())
            .andExpect(jsonPath("$.content.[*].informationCards").isEmpty());
    }

    @Test
    public void testFullBoardReturnedById() throws Exception {
        Board board = new Board();
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions(board);

        List<InformationCard> informationCard = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setInformationCards(informationCard);
        board.setColumnDefinitions(columnDefinitions);
        board.setName("Test");
        final Board savedBoard = boardRepository.save(board);

        boardResourceMockMVC.perform(get("/api/retro/boards/{id}", savedBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(savedBoard.getId().toString()))
            .andExpect(jsonPath("$.name").value("Test"))
            .andExpect(jsonPath("$.createdAt").isNotEmpty())
            .andExpect(jsonPath("$.columnDefinitions").isNotEmpty())
            .andExpect(jsonPath("$.informationCards").isNotEmpty());
    }

    @Test
    public void testCreateBoard() throws Exception {
        Board board = new Board();
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions(board);

        board.setColumnDefinitions(columnDefinitions);
        board.setName("Test");

        BoardDTO boardDTO = boardMapper.toDto(board);

        boardResourceMockMVC.perform(post("/api/retro/boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boardDTO)))
            .andExpect(status().isCreated());

        List<Board> allBoards = boardRepository.findAll();
        Assertions.assertThat(allBoards).hasSize(1);

        final Board savedBoard = allBoards.get(0);

        Assertions.assertThat(savedBoard.getId()).isNotNull();
        Assertions.assertThat(savedBoard.getName()).isEqualTo("Test");
        Assertions.assertThat(savedBoard.getCreatedAt()).isNotNull();
        final List<ColumnDefinition> savedBoardColumnDefinitions = savedBoard.getColumnDefinitions();
        for(int i = 0; i < savedBoard.getColumnDefinitions().size(); i++) {
            Assertions.assertThat(savedBoardColumnDefinitions.get(i)).isEqualToIgnoringGivenFields(columnDefinitions.get(i), "id", "board");
        }
    }

    @Test
    public void testDeleteBoard() throws Exception {
        Board board = new Board();
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions(board);

        List<InformationCard> informationCard = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setInformationCards(informationCard);
        board.setColumnDefinitions(columnDefinitions);
        board.setName("Test");

        final Board savedBoard = boardRepository.save(board);
        boardResourceMockMVC.perform(delete("/api/retro/boards/{id}", savedBoard.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());
        final List<Board> boards = boardRepository.findAll();
        Assertions.assertThat(boards).isEmpty();

    }

}
