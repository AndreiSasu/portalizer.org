package org.portalizer.demodata.steps;

import com.github.javafaker.Faker;
import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.domain.InformationCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakerCardsStep implements CardsStep {

    private final Faker faker;
    private final int maxCardsPerColumn;

    public FakerCardsStep(final Faker faker, final @Value("${portalizer.demo.max-cards-per-column}") int maxCardsPerColumn) {
        this.faker = faker;
        this.maxCardsPerColumn = maxCardsPerColumn;
    }

    @Override
    public void apply(Board board) {

        final List<InformationCard> informationCards = new ArrayList<>();
        final List<ColumnDefinition> columnDefinitions = board.getColumnDefinitions();
        final int max = faker.random().nextInt(0, maxCardsPerColumn);
        columnDefinitions.forEach(columnDefinition -> {
            for(int x = 0; x <= max; x++) {
                if(x % 3 == 0) continue;

                final InformationCard informationCard = new InformationCard();
                informationCard.setBoard(board);
                informationCard.setColumnKey(columnDefinition.getKey());

                informationCard.setText(seededText());
                informationCards.add(informationCard);
            }

        });

        board.setInformationCards(informationCards);
    }

    private String seededText() {
        final int textSeed = faker.random().nextInt(0, 3);

        if(textSeed % 2 == 0) {
            return faker.hitchhikersGuideToTheGalaxy().quote();
        }

        if(textSeed % 3 == 0) {
            return faker.buffy().quotes();
        }

        return faker.chuckNorris().fact();
    }
}
