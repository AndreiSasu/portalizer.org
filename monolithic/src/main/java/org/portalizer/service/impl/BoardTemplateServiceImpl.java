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

        List<ColumnDefinitionDTO> madSadGladColumns = Arrays.asList(new ColumnDefinitionDTO(ColumnType.MAD, "What makes me Mad"),
            new ColumnDefinitionDTO(ColumnType.SAD, "What makes me Sad"), new ColumnDefinitionDTO(ColumnType.GLAD, "What makes me Glad"));
        BoardTemplateDTO madSadGlad = new BoardTemplateDTO("Mad - Sad - Glad",  madSadGladColumns,"(Most simple 3 column board)");

        List<ColumnDefinitionDTO> wentWellToImproveActionItemsColumns = Arrays.asList(new ColumnDefinitionDTO(ColumnType.WENT_WELL, "Went Well"),
            new ColumnDefinitionDTO(ColumnType.TO_IMPROVE, "To Improve"),
            new ColumnDefinitionDTO(ColumnType.ACTION_ITEMS, "Action Items"));
        BoardTemplateDTO wentWellToImproveActionItems = new BoardTemplateDTO("Went Well - To Improve - Action Items", wentWellToImproveActionItemsColumns, "(Most simple 3 column board)");


        List<ColumnDefinitionDTO> kalmColumns = Arrays.asList(new ColumnDefinitionDTO(ColumnType.KEEP, "Keep"),
            new ColumnDefinitionDTO(ColumnType.ADD, "Add"),
            new ColumnDefinitionDTO(ColumnType.LESS, "Less"),
            new ColumnDefinitionDTO(ColumnType.MORE, "More"));
        BoardTemplateDTO kalm = new BoardTemplateDTO("KALM: Keep - Add - Less - More", kalmColumns, "(Most simple 3 column board)");


        List<ColumnDefinitionDTO> startStopContinueColumns = Arrays.asList(new ColumnDefinitionDTO(ColumnType.START, "Start"),
            new ColumnDefinitionDTO(ColumnType.STOP, "Stop"), new ColumnDefinitionDTO(ColumnType.CONTINUE, "Continue"));
        BoardTemplateDTO startStopContinue = new BoardTemplateDTO("Start - Stop - Continue",  startStopContinueColumns,"(Most simple 3 column board)");



        List<ColumnDefinitionDTO> likedLearnedLackedLongedForColumns = Arrays.asList(new ColumnDefinitionDTO(ColumnType.LIKED, "Liked"),
            new ColumnDefinitionDTO(ColumnType.LEARNED, "Learned"),
            new ColumnDefinitionDTO(ColumnType.LACKED, "Lacked"),
            new ColumnDefinitionDTO(ColumnType.LONGED_FOR, "Longed For"));
        BoardTemplateDTO likedLearnedLackedLongedFor = new BoardTemplateDTO("4Ls: Liked - Learned - Lacked - Longed For", likedLearnedLackedLongedForColumns, "(Most simple 3 column board)");


        List<ColumnDefinitionDTO> leanCoffeeColumns = Arrays.asList(new ColumnDefinitionDTO(ColumnType.TO_DISCUSS, "To Discuss"),
            new ColumnDefinitionDTO(ColumnType.DISCUSSING, "Discussing"), new ColumnDefinitionDTO(ColumnType.DISCUSSED, "Discussed"));
        BoardTemplateDTO leanCoffee = new BoardTemplateDTO("Lean Coffee: To Discuss - Discussing - Discussed",  leanCoffeeColumns,"(Most simple 3 column board)");

        List<ColumnDefinitionDTO> dakiColumns = Arrays.asList(new ColumnDefinitionDTO(ColumnType.DROP, "Drop"),
            new ColumnDefinitionDTO(ColumnType.ADD, "Add"),
            new ColumnDefinitionDTO(ColumnType.KEEP, "Keep"),
            new ColumnDefinitionDTO(ColumnType.IMPROVE, "Improve"));
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
