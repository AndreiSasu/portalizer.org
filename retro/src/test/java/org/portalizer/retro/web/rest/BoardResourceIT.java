package org.portalizer.retro.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.portalizer.retro.RetroApp;

import org.portalizer.retro.service.BoardService;
import org.portalizer.retro.web.rest.errors.ExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

import static org.portalizer.retro.web.rest.TestUtil.createFormattingConversionService;

@SpringBootTest(classes = RetroApp.class)
public class BoardResourceIT {

    @Autowired
    private BoardService boardService;

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
    public void testAllBoardsAreReturned() {

    }

    @Test
    public void testBoardReturnedById() {

    }

}
