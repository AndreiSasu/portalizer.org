package org.portalizer.retro;

import org.portalizer.retro.domain.Board;
import org.portalizer.retro.domain.ColumnDefinition;
import org.portalizer.retro.domain.InformationCard;
import org.portalizer.retro.repository.BoardRepository;
import org.portalizer.retro.utils.EntityUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.SortedSet;

@Component
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

    private BoardRepository boardRepository;

    public AppStartupRunner(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Your application started with option names : {}", args.getNonOptionArgs());
        if(args.getNonOptionArgs().contains("addtestdata")) {
            for(int i = 0; i < 10; i++) {
                SortedSet<ColumnDefinition> columnDefinitions = EntityUtils.buildColumnDefinitions();
                Board board = new Board();
                board.setName("Generated " + i);
                List<InformationCard> informationCard = EntityUtils.cardForEachColumn(board, columnDefinitions);
                board.setInformationCards(informationCard);
                board.setColumnDefinitions(columnDefinitions);
                boardRepository.save(board);
            }
        }
    }
}

