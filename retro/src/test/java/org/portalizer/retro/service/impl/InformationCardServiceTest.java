package org.portalizer.retro.service.impl;

import org.junit.jupiter.api.Test;
import org.portalizer.retro.RetroApp;
import org.portalizer.retro.domain.ColumnType;
import org.portalizer.retro.service.InformationCardService;
import org.portalizer.retro.service.dto.InformationCardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RetroApp.class)
public class InformationCardServiceTest {

    @Autowired
    private InformationCardService informationCardService;

    @Test
    public void testExceptionThrownIfBoardIdNotFoundForAdd() {
        InformationCardDTO informationCardDTO = new InformationCardDTO();
        informationCardDTO.setText("test");
        informationCardDTO.setColumnType(ColumnType.GLAD);
        informationCardService.add(informationCardDTO);
    }

    @Test
    public void testExceptionThrownIfBoardIdNotFoundForUpdate() {

    }

    @Test
    public void testExceptionThrownIfBoardDoesNotHaveColumnType() {

    }


}
