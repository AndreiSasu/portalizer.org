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
import java.util.UUID;

@Service
@Slf4j
public class BoardTemplateServiceImpl implements BoardTemplateService {

    private final List<BoardTemplateDTO> boardTemplateDTOS = new ArrayList<>();

    public BoardTemplateServiceImpl() {

        List<ColumnDefinitionDTO> madSadGladColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "What makes me Mad", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "What makes me Sad", 1), new ColumnDefinitionDTO(UUID.randomUUID(), "What makes me Glad", 2));
        BoardTemplateDTO madSadGlad = new BoardTemplateDTO("Mad - Sad - Glad",  madSadGladColumns,"(Most simple 3 column board)");

        List<ColumnDefinitionDTO> wentWellToImproveActionItemsColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Went Well", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "To Improve", 1),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Action Items", 2));
        BoardTemplateDTO wentWellToImproveActionItems = new BoardTemplateDTO("Went Well - To Improve - Action Items", wentWellToImproveActionItemsColumns, "(Most simple 3 column board)");


        List<ColumnDefinitionDTO> kalmColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Keep", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Add", 1),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Less", 2),
            new ColumnDefinitionDTO(UUID.randomUUID(), "More", 3));
        BoardTemplateDTO kalm = new BoardTemplateDTO("KALM: Keep - Add - Less - More", kalmColumns, "(Most simple 3 column board)");


        List<ColumnDefinitionDTO> startStopContinueColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Start", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Stop", 1), new ColumnDefinitionDTO(UUID.randomUUID(), "Continue", 2));
        BoardTemplateDTO startStopContinue = new BoardTemplateDTO("Start - Stop - Continue",  startStopContinueColumns,"(Most simple 3 column board)");



        List<ColumnDefinitionDTO> likedLearnedLackedLongedForColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Liked", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Learned", 1),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Lacked", 2),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Longed For", 3));
        BoardTemplateDTO likedLearnedLackedLongedFor = new BoardTemplateDTO("4Ls: Liked - Learned - Lacked - Longed For", likedLearnedLackedLongedForColumns, "(Most simple 3 column board)");


        List<ColumnDefinitionDTO> leanCoffeeColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "To Discuss", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Discussing", 1), new ColumnDefinitionDTO(UUID.randomUUID(), "Discussed", 2));
        BoardTemplateDTO leanCoffee = new BoardTemplateDTO("Lean Coffee: To Discuss - Discussing - Discussed",  leanCoffeeColumns,"(Most simple 3 column board)");

        List<ColumnDefinitionDTO> dakiColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Drop", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Add", 1),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Keep", 2),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Improve", 3));
        BoardTemplateDTO daki = new BoardTemplateDTO("DAKI: Drop - Add - Keep - Improve", dakiColumns, "(Most simple 3 column board)");


        boardTemplateDTOS.add(madSadGlad);
        boardTemplateDTOS.add(wentWellToImproveActionItems);
        boardTemplateDTOS.add(kalm);
        boardTemplateDTOS.add(startStopContinue);
        boardTemplateDTOS.add(likedLearnedLackedLongedFor);
        boardTemplateDTOS.add(leanCoffee);
        boardTemplateDTOS.add(daki);

    }

    @Override
    public List<BoardTemplateDTO> getBoardTemplates() {
        return boardTemplateDTOS;
    }
}
