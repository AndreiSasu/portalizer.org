package org.portalizer;

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
import java.util.SortedSet;

@Component
@Profile("prod")
public class SetupTestData implements ApplicationRunner {

    private BoardRepository boardRepository;

    public SetupTestData(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 10; i++) {
            List<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
            Board board = new Board();
            board.setName("Generated Board Name: " + i + " for Sprint " + i);
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
