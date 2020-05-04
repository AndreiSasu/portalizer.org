package org.portalizer.repository;

import org.assertj.core.api.Assertions;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.junit.jupiter.api.*;
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
public class HibernateIndexingIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeAll
    @Commit
    public void setup() {
        boardRepository.deleteAll();
        final Board board = EntityUtils.validBoard();
        boardRepository.save(board);
    }

    @Test
    public void testA_whenIndexInitialized_thenCorrectIndexSize() throws InterruptedException {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer()
            .startAndWait();
        int indexSize = fullTextEntityManager.getSearchFactory()
            .getStatistics()
            .getNumberOfIndexedEntities(Board.class.getName());

        Assertions.assertThat(indexSize == 2).isTrue();
    }

    @Test
    @Commit
    public void testB_addNewBoard() {
        boardRepository.save(EntityUtils.validBoard());
    }


    @Test
    @Commit
    public void testC_NewEntitiesIndexedAutomatically() {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        int indexSize = fullTextEntityManager.getSearchFactory()
            .getStatistics()
            .getNumberOfIndexedEntities(Board.class.getName());

        Assertions.assertThat(indexSize == 2).isTrue();
    }

}
