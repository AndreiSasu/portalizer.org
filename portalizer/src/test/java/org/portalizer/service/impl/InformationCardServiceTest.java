package org.portalizer.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.portalizer.PortalizerApp;
import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnType;
import org.portalizer.domain.InformationCard;
import org.portalizer.repository.BoardRepository;
import org.portalizer.repository.InformationCardRepository;
import org.portalizer.service.InformationCardService;
import org.portalizer.service.dto.AddCardDTO;
import org.portalizer.service.dto.InformationCardDTO;
import org.portalizer.service.dto.UpdateCardDTO;
import org.portalizer.service.mapper.InformationCardMapper;
import org.portalizer.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.UUID;

@SpringBootTest(classes = PortalizerApp.class)
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
    public void testAddCardDTOValidation() {
        AddCardDTO addCardDTO = new AddCardDTO();
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.add(addCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("boardId")
            .hasMessageContaining("columnKey")
            .hasMessageContaining("text");
    }

    @Test
    public void testExceptionThrownIfBoardIdNotFoundForCreate() {
        AddCardDTO addCardDTO = new AddCardDTO();
        addCardDTO.setColumnKey(UUID.randomUUID());
        addCardDTO.setText("Test");

        final UUID doesNotExist = UUID.randomUUID();
        addCardDTO.setBoardId(doesNotExist);

        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.add(addCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining(String.format("Board with id: %s does not exist!", doesNotExist));
    }

    @Test
    public void testExceptionThrownIfCardIdNotFoundForUpdate() {
        UpdateCardDTO updateCardDTO = new UpdateCardDTO();
        updateCardDTO.setBoardId(savedBoard.getId());
        updateCardDTO.setColumnKey(UUID.randomUUID());
        updateCardDTO.setText("Some text");
        final UUID cardId = UUID.randomUUID();
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.update(cardId, updateCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining(String.format("Board with board id: %s and card id: %s does not exist!", updateCardDTO.getBoardId(),
                cardId));
    }

    @Test
    public void testExceptionThrownIfBoardIdNotFoundForUpdate() {
        UpdateCardDTO updateCardDTO = new UpdateCardDTO();
        updateCardDTO.setColumnKey(UUID.randomUUID());
        updateCardDTO.setText("Some text");
        final UUID doesNotExist = UUID.randomUUID();
        updateCardDTO.setBoardId(doesNotExist);
        final UUID cardId = UUID.randomUUID();
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.update(cardId, updateCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining(String.format("Board with board id: %s and card id: %s does not exist!", updateCardDTO.getBoardId(),
                cardId));
    }

    @Test
    public void testExceptionThrownAddIfBoardDoesNotHaveColumnKey() {
        AddCardDTO addCardDTO = new AddCardDTO();
        addCardDTO.setText("Some text");
        addCardDTO.setBoardId(savedBoard.getId());
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.add(addCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining("columnKey");
    }

    @Test
    public void testExceptionThrownUpdateIfBoardDoesNotHaveColumnKey() {
        final InformationCard informationCard = savedBoard.getInformationCards().get(0);
        final UpdateCardDTO updateCardDTO = new UpdateCardDTO();
        updateCardDTO.setColumnKey(null);
        updateCardDTO.setBoardId(informationCard.getBoard().getId());
        updateCardDTO.setText(informationCard.getText());
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.update(UUID.randomUUID(), updateCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining("columnKey");
    }

    @Test
    public void testUpdateExistingInformationCardHappyPath() {
        final InformationCard beforeUpdate = savedBoard.getInformationCards().get(0);
        final UpdateCardDTO updateCardDTO = new UpdateCardDTO();
        updateCardDTO.setText("This has changed");
        updateCardDTO.setBoardId(beforeUpdate.getBoard().getId());
        updateCardDTO.setColumnKey(beforeUpdate.getColumnKey());
        informationCardService.update(beforeUpdate.getId(), updateCardDTO);

        final InformationCard afterUpdate = informationCardRepository.findById(beforeUpdate.getId()).get();
        Assertions.assertThat(afterUpdate.getText()).isEqualTo("This has changed");
        Assertions.assertThat(afterUpdate.getCreatedAt()).isEqualTo(beforeUpdate.getCreatedAt());
        Assertions.assertThat(afterUpdate.getUpdatedAt()).isAfter(beforeUpdate.getUpdatedAt());
        Assertions.assertThat(afterUpdate.getColumnKey()).isEqualTo(beforeUpdate.getColumnKey());
        Assertions.assertThat(afterUpdate.getBoard().getId()).isEqualTo(beforeUpdate.getBoard().getId());
    }

    @Test
    public void testDeleteById() {
        final InformationCard toDelete = savedBoard.getInformationCards().get(0);
        final UUID deletedId = toDelete.getId();
        int sizeBefore = savedBoard.getInformationCards().size();
        informationCardService.delete(deletedId);
        int sizeAfter = informationCardRepository.findAllByBoardId(savedBoard.getId()).get().size();
        Assertions.assertThat(sizeAfter).isEqualTo(sizeBefore - 1);
        Assertions.assertThat(informationCardRepository.findById(deletedId)).isNotPresent();
    }

    @Test
    public void testAddInformationCardHappyPath() {
        final UUID columnKey = UUID.randomUUID();
        final AddCardDTO toAdd = new AddCardDTO();
        toAdd.setBoardId(savedBoard.getId());
        toAdd.setText("Added text");
        toAdd.setColumnKey(columnKey);

        final InformationCardDTO added = informationCardService.add(toAdd);

        final InformationCard afterAdd = informationCardRepository.findById(added.getId()).get();
        Assertions.assertThat(afterAdd.getText()).isEqualTo("Added text");
        Assertions.assertThat(afterAdd.getCreatedAt()).isNotNull();
        Assertions.assertThat(afterAdd.getUpdatedAt()).isEqualToIgnoringNanos(afterAdd.getCreatedAt());
        Assertions.assertThat(afterAdd.getColumnKey()).isEqualTo(columnKey);
        Assertions.assertThat(afterAdd.getBoard().getId()).isEqualTo(savedBoard.getId());


        Assertions.assertThat(added.getText()).isEqualTo("Added text");
        Assertions.assertThat(added.getCreatedAt()).isNotNull();
        Assertions.assertThat(added.getUpdatedAt()).isEqualToIgnoringNanos(afterAdd.getCreatedAt());
        Assertions.assertThat(added.getColumnKey()).isEqualTo(afterAdd.getColumnKey());
        Assertions.assertThat(added.getBoardId()).isEqualTo(savedBoard.getId());
    }


}
