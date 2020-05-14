package org.portalizer.demodata.steps;

import com.github.javafaker.Faker;
import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakerColumnsStep implements ColumnsStep {

    private final Faker faker;
    private final int maxRandomColumns;

    public FakerColumnsStep(final Faker faker, @Value("${portalizer.demo.max-random-columns}") final int maxRandomColumns) {
        this.faker = faker;
        this.maxRandomColumns = maxRandomColumns;
    }

    @Override
    public void apply(Board board) {

        final boolean usePredefinedTemplate = true;
        List<ColumnDefinition> columnDefinitions;

        if(usePredefinedTemplate) {
            columnDefinitions = EntityUtils.buildRandomColumnDefinitionsFromTemplate(board);
        } else {

            final int totalColumns = faker.random().nextInt(0, maxRandomColumns);
            columnDefinitions = new ArrayList<>(totalColumns);

            for(int i = 0; i <= totalColumns; i++) {
                columnDefinitions.add(EntityUtils.columnDefinition(board, faker.lorem().word()));
            }

        }

        board.setColumnDefinitions(columnDefinitions);
    }
}
