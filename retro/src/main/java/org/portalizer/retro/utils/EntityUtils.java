package org.portalizer.retro.utils;

import org.portalizer.retro.domain.Board;
import org.portalizer.retro.domain.ColumnDefinition;
import org.portalizer.retro.domain.ColumnType;
import org.portalizer.retro.domain.InformationCard;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class EntityUtils {

    public static List<InformationCard> cardForEachColumn(final Board board, final SortedSet<ColumnDefinition> columnDefinitions) {
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

    public static SortedSet<ColumnDefinition> buildColumnDefinitions() {
        ColumnDefinition mad = new ColumnDefinition();
        mad.setColumnType(ColumnType.MAD);
        mad.setTitle("What Makes me Mad");

        ColumnDefinition sad = new ColumnDefinition();
        sad.setColumnType(ColumnType.SAD);
        sad.setTitle("What Makes me Sad");

        ColumnDefinition glad = new ColumnDefinition();
        glad.setColumnType(ColumnType.GLAD);
        glad.setTitle("What Makes me Glad");

        SortedSet<ColumnDefinition> columnDefinitions = new TreeSet<>();
        columnDefinitions.add(mad);
        columnDefinitions.add(sad);
        columnDefinitions.add(glad);
        return columnDefinitions;
    }
}
