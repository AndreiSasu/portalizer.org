package org.portalizer.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.portalizer.PortalizerApp;
import org.portalizer.domain.Board;
import org.portalizer.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest(classes = PortalizerApp.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HibernateSearchIntegrationTest {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeAll
    @Commit
    public void setup() {
        boardRepository.deleteAll();
        for (int i = 0; i < 4; i++) {
            final Board board = EntityUtils.validBoard();
            if (i < 1) {
                board.setName("Gigi");
                board.setDescription("Steaua Bucuresti");
            }
            if (i == 2) {
                board.setName("Gica");
                board.setDescription("Football Captain");
            }
            if (i == 3) {
                board.setName("Nicu");
                board.setDescription("Paleru I believe comedian");
            }
            if (i == 4) {
                board.setName("Dorel");
                board.setDescription("jack of all trades");
            }
            boardRepository.save(board);
        }

    }


    @Test
    public void testSearchBoardByDescription() {

    }


    @Test
    public void testSearchBoardByName() {

    }
}
