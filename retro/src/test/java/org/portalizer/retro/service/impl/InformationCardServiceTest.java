package org.portalizer.retro.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.portalizer.retro.RetroApp;
import org.portalizer.retro.domain.Board;
import org.portalizer.retro.domain.ColumnDefinition;
import org.portalizer.retro.domain.ColumnType;
import org.portalizer.retro.domain.InformationCard;
import org.portalizer.retro.repository.BoardRepository;
import org.portalizer.retro.repository.InformationCardRepository;
import org.portalizer.retro.service.InformationCardService;
import org.portalizer.retro.service.dto.InformationCardDTO;
import org.portalizer.retro.service.mapper.InformationCardMapper;
import org.portalizer.retro.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.UUID;

@SpringBootTest(classes = RetroApp.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InformationCardServiceTest {

    @Autowired
    private InformationCardService informationCardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private InformationCardRepository informationCardRepository;

    @Autowired
    private InformationCardMapper informationCardMapper;

    private Board savedBoard;

    @BeforeAll
    public void setup() {
        savedBoard = boardRepository.save(EntityUtils.validBoard());
    }

    @Test
    public void testInformationCardDTOValidation() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.add(informationCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("boardId")
            .hasMessageContaining("columnType")
            .hasMessageContaining("text");
    }

    @Test
    public void testExceptionThrownIfBoardIdNotFoundForCreate() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        informationCardDTO.setColumnType(ColumnType.GLAD);
        informationCardDTO.setText("Test");

        final UUID doesNotExist = UUID.randomUUID();
        informationCardDTO.setBoardId(doesNotExist);

        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.add(informationCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining(String.format("Board with id: %s does not exist!", doesNotExist));
    }

    @Test
    public void testExceptionThrownIfCardIdNotFoundForUpdate() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        informationCardDTO.setBoardId(savedBoard.getId());
        informationCardDTO.setColumnType(ColumnType.GLAD);
        informationCardDTO.setText("Some text");

        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.update(informationCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining(String.format("Board with board id: %s and card id: %s does not exist!", informationCardDTO.getBoardId(),
                informationCardDTO.getCardId()));
    }


    @Test
    public void testExceptionThrownIfBoardIdNotFoundForUpdate() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        informationCardDTO.setColumnType(ColumnType.GLAD);
        informationCardDTO.setText("Some text");
        final UUID doesNotExist = UUID.randomUUID();
        informationCardDTO.setBoardId(doesNotExist);

        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.update(informationCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining(String.format("Board with board id: %s and card id: %s does not exist!", informationCardDTO.getBoardId(),
                informationCardDTO.getCardId()));
    }

    @Test
    public void testExceptionThrownIfBoardDoesNotHaveColumnType() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        informationCardDTO.setText("Some text");
    }


}
