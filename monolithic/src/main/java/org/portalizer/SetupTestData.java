package org.portalizer;

import com.github.javafaker.Faker;
import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.domain.InformationCard;
import org.portalizer.repository.BoardRepository;
import org.portalizer.utils.EntityUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("testdata")
public class SetupTestData implements ApplicationRunner {

    private BoardRepository boardRepository;
    private Faker faker = new Faker();

    public SetupTestData(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (int i = 0; i < 500; i++) {
            Board board = new Board();
            List<ColumnDefinition> columnDefinitions = EntityUtils.buildRandomColumnDefinitionsFromTemplate(board);

            board.setName(faker.company().bs());
            board.setDescription(faker.shakespeare().asYouLikeItQuote());
            final List<InformationCard> informationCards = new ArrayList<>();
            for(int x = 0; x < 10; x++) {
                informationCards.addAll(EntityUtils.cardForEachColumn(board, columnDefinitions));
            }

            board.setInformationCards(informationCards);
            board.setColumnDefinitions(columnDefinitions);
            boardRepository.save(board);
        }
    }
}
