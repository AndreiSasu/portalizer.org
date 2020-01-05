package org.portalizer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.portalizer.domain.ColumnType;
import org.portalizer.service.BoardTemplateService;
import org.portalizer.service.dto.BoardTemplateDTO;
import org.portalizer.service.dto.ColumnDefinitionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class BoardTemplateServiceImpl implements BoardTemplateService {

    private final List<BoardTemplateDTO> boardTemplateDTOS = new ArrayList<>();

    public BoardTemplateServiceImpl() {

        List<ColumnDefinitionDTO> madSadGladColumns = Arrays.asList(new ColumnDefinitionDTO(ColumnType.MAD, "Mad"),
            new ColumnDefinitionDTO(ColumnType.SAD, "Sad"), new ColumnDefinitionDTO(ColumnType.GLAD, "Glad"));
        BoardTemplateDTO madSadGlad = new BoardTemplateDTO("Mad - Sad - Glad",  madSadGladColumns,"(Most simple 3 column board)");

        List<ColumnDefinitionDTO> wentWellToImproveActionItemsColumns = Arrays.asList(new ColumnDefinitionDTO(ColumnType.WENT_WELL, "Went Well"),
            new ColumnDefinitionDTO(ColumnType.TO_IMPROVE, "To Improve"),
            new ColumnDefinitionDTO(ColumnType.ACTION_ITEMS, "Action Items"));
        BoardTemplateDTO wentWellToImproveActionItems = new BoardTemplateDTO("Went Well - To Improve - Action Items", wentWellToImproveActionItemsColumns, "(Most simple 3 column board)");

        boardTemplateDTOS.add(madSadGlad);
        boardTemplateDTOS.add(wentWellToImproveActionItems);
    }

    @Override
    public List<BoardTemplateDTO> getBoardTemplates() {
        return boardTemplateDTOS;
    }
}
