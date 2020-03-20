package org.portalizer.utils;

import com.github.javafaker.Faker;
import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.domain.InformationCard;
import org.portalizer.service.BoardTemplateService;
import org.portalizer.service.dto.BoardTemplateDTO;
import org.portalizer.service.impl.BoardTemplateServiceImpl;
import org.portalizer.service.mapper.ColumnDefinitionMapper;
import org.portalizer.service.mapper.ColumnDefinitionMapperImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EntityUtils {

    public static Board validBoard(final String name, final String description) {
        Board board = new Board();
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions(board);
        List<InformationCard> informationCards = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setColumnDefinitions(columnDefinitions);
        board.setInformationCards(informationCards);
        board.setName(name);
        board.setDescription(description);
        return board;
    }

    public static Board validBoard() {
        Board board = new Board();
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions(board);
        List<InformationCard> informationCards = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setColumnDefinitions(columnDefinitions);
        board.setInformationCards(informationCards);
        board.setName("Test");
        return board;
    }

    public static List<InformationCard> cardForEachColumn(final Board board, final List<ColumnDefinition> columnDefinitions) {
        final Faker faker = new Faker();
        final List<InformationCard> informationCards = new ArrayList<>();
        columnDefinitions.forEach(columnDefinition -> {
            final InformationCard informationCard = new InformationCard();
            informationCard.setBoard(board);
            informationCard.setColumnKey(columnDefinition.getKey());
            informationCard.setText(faker.hitchhikersGuideToTheGalaxy().quote());
            informationCards.add(informationCard);
        });
        return informationCards;
    }

    public static List<ColumnDefinition> buildRandomColumnDefinitionsFromTemplate(final Board board) {
        final BoardTemplateService boardTemplateService = new BoardTemplateServiceImpl();
        final ColumnDefinitionMapper columnDefinitionMapper = new ColumnDefinitionMapperImpl();
        final int size = boardTemplateService.getBoardTemplates().size();
        final BoardTemplateDTO boardTemplateDTO = boardTemplateService.getBoardTemplates().get(new Random().nextInt(size - 1));
        final List<ColumnDefinition> columnDefinitions = columnDefinitionMapper.toEntity(boardTemplateDTO.getBoardColumns());
        columnDefinitions.forEach(columnDefinition -> columnDefinition.setBoard(board));
        return columnDefinitions;
    }

    public static List<ColumnDefinition> buildRandomColumnDefinitions(final Board board, final int maxColumns) {
        final BoardTemplateService boardTemplateService = new BoardTemplateServiceImpl();
        final ColumnDefinitionMapper columnDefinitionMapper = new ColumnDefinitionMapperImpl();
        final int size = boardTemplateService.getBoardTemplates().size();
        final BoardTemplateDTO boardTemplateDTO = boardTemplateService.getBoardTemplates().get(new Random().nextInt(size - 1));
        final List<ColumnDefinition> columnDefinitions = columnDefinitionMapper.toEntity(boardTemplateDTO.getBoardColumns());
        columnDefinitions.forEach(columnDefinition -> columnDefinition.setBoard(board));
        return columnDefinitions;
    }

    public static List<ColumnDefinition> buildColumnDefinitions(final Board board) {
        ColumnDefinition mad = new ColumnDefinition();
        mad.setKey(UUID.randomUUID());
        mad.setTitle("Mad");
        mad.setBoard(board);

        ColumnDefinition sad = new ColumnDefinition();
        sad.setKey(UUID.randomUUID());
        sad.setTitle("Sad");
        sad.setBoard(board);

        ColumnDefinition glad = new ColumnDefinition();
        glad.setKey(UUID.randomUUID());
        glad.setTitle("Glad");
        glad.setBoard(board);

        List<ColumnDefinition> columnDefinitions = new ArrayList<>();
        columnDefinitions.add(mad);
        columnDefinitions.add(sad);
        columnDefinitions.add(glad);
        return columnDefinitions;
    }

    public static ColumnDefinition columnDefinition(final Board board, final String title) {
        ColumnDefinition columnDefinition = new ColumnDefinition();
        columnDefinition.setBoard(board);
        columnDefinition.setTitle(title);
        columnDefinition.setKey(UUID.randomUUID());
        return columnDefinition;
    }
}
