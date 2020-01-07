package org.portalizer.utils;

import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.domain.ColumnType;
import org.portalizer.domain.InformationCard;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {


    public static Board validBoard() {
        List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
        Board board = new Board();
        List<InformationCard> informationCards = EntityUtils.cardForEachColumn(board, columnDefinitions);
        board.setColumnDefinitions(columnDefinitions);
        board.setInformationCards(informationCards);
        board.setName("Test");
        return board;
    }

    public static List<InformationCard> cardForEachColumn(final Board board, final List<ColumnDefinition> columnDefinitions) {
        final List<InformationCard> informationCards = new ArrayList<>();
        columnDefinitions.forEach(columnDefinition -> {
            final InformationCard informationCard = new InformationCard();
            informationCard.setBoard(board);
            informationCard.setColumnType(columnDefinition.getColumnType());
            informationCard.setText("Here is something that makes me " + columnDefinition.getColumnType());
            informationCards.add(informationCard);
        });
        return informationCards;
    }

    public static List<ColumnDefinition> buildColumnDefinitions() {
        ColumnDefinition mad = new ColumnDefinition();
        mad.setColumnType(ColumnType.MAD);
        mad.setTitle("What Makes me Mad");

        ColumnDefinition sad = new ColumnDefinition();
        sad.setColumnType(ColumnType.SAD);
        sad.setTitle("What Makes me Sad");

        ColumnDefinition glad = new ColumnDefinition();
        glad.setColumnType(ColumnType.GLAD);
        glad.setTitle("What Makes me Glad");

        List<ColumnDefinition> columnDefinitions = new ArrayList<>();
        columnDefinitions.add(mad);
        columnDefinitions.add(sad);
        columnDefinitions.add(glad);
        return columnDefinitions;
    }
}
