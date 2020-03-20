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
import org.portalizer.service.dto.InformationCardDTO;
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
    public void testInformationCardDTOValidation() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.add(informationCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("boardId")
            .hasMessageContaining("columnKey")
            .hasMessageContaining("text");
    }

    @Test
    public void testExceptionThrownIfBoardIdNotFoundForCreate() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        informationCardDTO.setColumnKey(UUID.randomUUID());
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
        informationCardDTO.setColumnKey(UUID.randomUUID());
        informationCardDTO.setText("Some text");

        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.update(informationCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining(String.format("Board with board id: %s and card id: %s does not exist!", informationCardDTO.getBoardId(),
                informationCardDTO.getId()));
    }

    @Test
    public void testExceptionThrownIfBoardIdNotFoundForUpdate() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        informationCardDTO.setColumnKey(UUID.randomUUID());
        informationCardDTO.setText("Some text");
        final UUID doesNotExist = UUID.randomUUID();
        informationCardDTO.setBoardId(doesNotExist);

        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.update(informationCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining(String.format("Board with board id: %s and card id: %s does not exist!", informationCardDTO.getBoardId(),
                informationCardDTO.getId()));
    }

    @Test
    public void testExceptionThrownAddIfBoardDoesNotHaveColumnKey() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        informationCardDTO.setText("Some text");
        informationCardDTO.setBoardId(savedBoard.getId());
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.add(informationCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining("columnKey");
    }

    @Test
    public void testExceptionThrownUpdateIfBoardDoesNotHaveColumnKey() {
        final InformationCard informationCard = savedBoard.getInformationCards().get(0);
        final InformationCardDTO informationCardDTO = informationCardMapper.toDto(informationCard);
        informationCardDTO.setColumnKey(null);
        final Throwable throwable = Assertions.catchThrowable(() -> informationCardService.update(informationCardDTO));
        Assertions.assertThat(throwable).isInstanceOf(ValidationException.class)
            .hasMessageContaining("columnKey");
    }

    @Test
    public void testUpdateExistingInformationCardHappyPath() {
        final InformationCard beforeUpdate = savedBoard.getInformationCards().get(0);
        final InformationCardDTO informationCardDTO = informationCardMapper.toDto(beforeUpdate);
        informationCardDTO.setText("This has changed");
        informationCardService.update(informationCardDTO);

        final InformationCard afterUpdate = informationCardRepository.findById(informationCardDTO.getId()).get();
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
        final InformationCardDTO toAdd = new InformationCardDTO();
        toAdd.setBoardId(savedBoard.getId());
        toAdd.setText("Added text");
        toAdd.setColumnKey(columnKey);

        final InformationCardDTO added = informationCardService.add(toAdd);

        final InformationCard afterAdd = informationCardRepository.findById(added.getId()).get();
        Assertions.assertThat(afterAdd.getText()).isEqualTo("Added text");
        Assertions.assertThat(afterAdd.getCreatedAt()).isNotNull();
        Assertions.assertThat(afterAdd.getUpdatedAt()).isEqualTo(afterAdd.getCreatedAt());
        Assertions.assertThat(afterAdd.getColumnKey()).isEqualTo(columnKey);
        Assertions.assertThat(afterAdd.getBoard().getId()).isEqualTo(savedBoard.getId());


        Assertions.assertThat(added.getText()).isEqualTo("Added text");
        Assertions.assertThat(added.getCreatedAt()).isNotNull();
        Assertions.assertThat(added.getUpdatedAt()).isEqualTo(afterAdd.getCreatedAt());
        Assertions.assertThat(added.getColumnKey()).isEqualTo(afterAdd.getColumnKey());
        Assertions.assertThat(added.getBoardId()).isEqualTo(savedBoard.getId());
    }


}
