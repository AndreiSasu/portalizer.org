package org.portalizer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.portalizer.domain.ColumnType;
import org.portalizer.service.BoardTemplateService;
import org.portalizer.service.dto.BoardTemplateDTO;
import org.portalizer.service.dto.ColumnDefinitionDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BoardTemplateServiceImpl implements BoardTemplateService {

    private final List<BoardTemplateDTO> boardTemplateDTOS = new ArrayList<>();
    private final BoardTemplateDTO emptyBoard = new BoardTemplateDTO("Empty Board", Collections.emptyList(), "Start from this board and create your own format by adding new columns.");
    public BoardTemplateServiceImpl() {

        List<ColumnDefinitionDTO> madSadGladColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Mad", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Sad", 1), new ColumnDefinitionDTO(UUID.randomUUID(), "Glad", 2));
        BoardTemplateDTO madSadGlad = new BoardTemplateDTO("Mad - Sad - Glad",  madSadGladColumns,
            "Mad, Sad, Glad is a popular retrospective technique that encourages team members to think about their emotions. " +
                "How does the team feel after the last sprint? " +
                "Mad, Sad, Glad is simple to learn while being easy to explain and run.\n");

        List<ColumnDefinitionDTO> wentWellToImproveActionItemsColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Went Well", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "To Improve", 1),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Action Items", 2));
        BoardTemplateDTO wentWellToImproveActionItems = new BoardTemplateDTO("Went Well - To Improve - Action Items", wentWellToImproveActionItemsColumns,
            "Focuses on the strengths and weaknesses of the team based on the previous sprint.");


        List<ColumnDefinitionDTO> kalmColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Keep", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Add", 1),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Less", 2),
            new ColumnDefinitionDTO(UUID.randomUUID(), "More", 3));
        BoardTemplateDTO kalm = new BoardTemplateDTO("KALM: Keep - Add - Less - More", kalmColumns,
            "KALM (Keep, Add, More, less) is a retrospective activity that fosters the conversation to the current activities and the perceived value. \n" +
                "It helps team members to understand each other perceived value on such practices.\n");


        List<ColumnDefinitionDTO> startStopContinueColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Start", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Stop", 1), new ColumnDefinitionDTO(UUID.randomUUID(), "Continue", 2));
        BoardTemplateDTO startStopContinue = new BoardTemplateDTO("Start - Stop - Continue",  startStopContinueColumns,"Start, Stop, Continue is an action-oriented retrospective technique that encourages participants to come up with practical ideas for team-based improvement.\n");



        List<ColumnDefinitionDTO> likedLearnedLackedLongedForColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Liked", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Learned", 1),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Lacked", 2),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Longed For", 3));
        BoardTemplateDTO likedLearnedLackedLongedFor = new BoardTemplateDTO("4Ls: Liked - Learned - Lacked - Longed For", likedLearnedLackedLongedForColumns, "4Ls stands for Liked Learned Lacked Longed For. " +
            "The 4Ls technique is a brainstorming technique for collecting feedback on a recently completed project or piece of work. " +
            "This retrospective highlight the positive (liked & learned) as well as the negative (lacked & longed for). The team have to think mostly from a factual (what happened) perspective, rather than an emotional perspective.\n");


        List<ColumnDefinitionDTO> leanCoffeeColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "To Discuss", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Discussing", 1), new ColumnDefinitionDTO(UUID.randomUUID(), "Discussed", 2));
        BoardTemplateDTO leanCoffee = new BoardTemplateDTO("Lean Coffee: To Discuss - Discussing - Discussed",  leanCoffeeColumns,"From the Lean Coffee website, we have that “Lean Coffee is a structured, but agenda-less meeting. " +
            "Participants gather, build an agenda, and begin talking. " +
            "Conversations are directed and productive because the agenda for the meeting was democratically generated.”\n");

        List<ColumnDefinitionDTO> dakiColumns = Arrays.asList(new ColumnDefinitionDTO(UUID.randomUUID(), "Drop", 0),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Add", 1),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Keep", 2),
            new ColumnDefinitionDTO(UUID.randomUUID(), "Improve", 3));
        BoardTemplateDTO daki = new BoardTemplateDTO("DAKI: Drop - Add - Keep - Improve", dakiColumns, "The DAKI (or Drop Add Keep Improve) retrospective activity is suitable to use after several sprints working with the same team. " +
            "As your team members are experimenting with different processes, it helps cull out non-value things, keep what is working, and improve by brainstorming on new ways to work.\n");

        boardTemplateDTOS.add(emptyBoard);
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
