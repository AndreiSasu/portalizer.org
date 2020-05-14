package org.portalizer.demodata;

import lombok.extern.slf4j.Slf4j;
import org.portalizer.repository.BoardRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@Profile("demo")
public class RefreshDemoBoards {

    private SetupDemoBoards setupDemoBoards;
    private BoardRepository boardRepository;

    public RefreshDemoBoards(final SetupDemoBoards setupDemoBoards,
                             final BoardRepository boardRepository) {
        this.setupDemoBoards = setupDemoBoards;
        this.boardRepository = boardRepository;
    }

    @Scheduled(cron = "${portalizer.demo.boards.refresh-cron}")
    public void refreshAllBoards() {
        boardRepository.deleteAll();
        this.setupDemoBoards.run(null);
    }

}
