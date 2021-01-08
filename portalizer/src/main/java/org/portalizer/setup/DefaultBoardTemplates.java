package org.portalizer.setup;

import org.portalizer.service.dto.BoardTemplateDTO;
import org.portalizer.service.dto.BoardTemplateColumnDefinitionDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DefaultBoardTemplates {

    private static final BoardTemplateDTO emptyBoard = new BoardTemplateDTO("Empty board", Collections.emptyList(), "Start from this board and create your own format by adding new columns.");

    private DefaultBoardTemplates() {
    }

    protected static BoardTemplateDTO wentWellToImproveActionItems() {
        List<BoardTemplateColumnDefinitionDTO> wentWellToImproveActionItemsColumns = Arrays.asList(new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Went Well", 0),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "To Improve", 1),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Action Items", 2));
        return new BoardTemplateDTO("Went Well - To Improve - Action Items", wentWellToImproveActionItemsColumns,
            "Focuses on the strengths and weaknesses of the team based on the previous sprint.");
    }

    protected static BoardTemplateDTO kalm() {
        List<BoardTemplateColumnDefinitionDTO> kalmColumns = Arrays.asList(new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Keep", 0),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Add", 1),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Less", 2),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "More", 3));
        return new BoardTemplateDTO("KALM: Keep - Add - Less - More", kalmColumns,
            "KALM (Keep, Add, More, less) is a retrospective activity that fosters the conversation to the current activities and the perceived value. \n" +
                "It helps team members to understand each other perceived value on such practices.\n");
    }

    protected static BoardTemplateDTO startStopContinue() {
        List<BoardTemplateColumnDefinitionDTO> startStopContinueColumns = Arrays.asList(new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Start", 0),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Stop", 1), new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Continue", 2));
        return new BoardTemplateDTO("Start - Stop - Continue",  startStopContinueColumns,"Start, Stop, Continue is an action-oriented retrospective technique that encourages participants to come up with practical ideas for team-based improvement.\n");
    }

    protected static BoardTemplateDTO likedLearnedLackedLongedFor() {
        List<BoardTemplateColumnDefinitionDTO> likedLearnedLackedLongedForColumns = Arrays.asList(new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Liked", 0),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Learned", 1),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Lacked", 2),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Longed For", 3));
        return new BoardTemplateDTO("4Ls: Liked - Learned - Lacked - Longed For", likedLearnedLackedLongedForColumns, "4Ls stands for Liked Learned Lacked Longed For. " +
            "The 4Ls technique is a brainstorming technique for collecting feedback on a recently completed project or piece of work. " +
            "This retrospective highlight the positive (liked & learned) as well as the negative (lacked & longed for). The team have to think mostly from a factual (what happened) perspective, rather than an emotional perspective.\n");
    }

    protected static BoardTemplateDTO leanCoffee() {
        List<BoardTemplateColumnDefinitionDTO> leanCoffeeColumns = Arrays.asList(new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "To Discuss", 0),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Discussing", 1), new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Discussed", 2));
        return new BoardTemplateDTO("Lean Coffee: To Discuss - Discussing - Discussed",  leanCoffeeColumns,"From the Lean Coffee website, we have that “Lean Coffee is a structured, but agenda-less meeting. " +
            "Participants gather, build an agenda, and begin talking. " +
            "Conversations are directed and productive because the agenda for the meeting was democratically generated.”\n");
    }

    protected static BoardTemplateDTO daki() {
        List<BoardTemplateColumnDefinitionDTO> dakiColumns = Arrays.asList(new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Drop", 0),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Add", 1),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Keep", 2),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Improve", 3));
        return new BoardTemplateDTO("DAKI: Drop - Add - Keep - Improve", dakiColumns, "The DAKI (or Drop Add Keep Improve) retrospective activity is suitable to use after several sprints working with the same team. " +
            "As your team members are experimenting with different processes, it helps cull out non-value things, keep what is working, and improve by brainstorming on new ways to work.\n");
    }

    protected static BoardTemplateDTO starfish() {
        List<BoardTemplateColumnDefinitionDTO> starFishColumns = Arrays.asList(new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Keep Doing", 0),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Less Of", 1),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "More Of", 2),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Stop Doing", 3),
            new BoardTemplateColumnDefinitionDTO(UUID.randomUUID(), "Start Doing", 4));
        return new BoardTemplateDTO("Starfish: Keep Doing - Less Of - More Of - Stop Doing - Start Doing", starFishColumns,
            "Starfish is a great data gathering activity to foster the thinking around practices and the value the team get from it. It helps team members to understand each other perceived value on such practices.\n");
    }

    public static List<BoardTemplateDTO> get() {
        return List.of(emptyBoard,
            wentWellToImproveActionItems(),
            startStopContinue(),
            kalm(),
            likedLearnedLackedLongedFor(),
            leanCoffee(),
            daki(),
            starfish());
    }

}
