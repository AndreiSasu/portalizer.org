package org.portalizer.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.portalizer.PortalizerApp;
import org.portalizer.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest(classes = PortalizerApp.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HibernateSearchPaginationIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BoardRepository boardRepository;

    private List<Board> boards;

    @BeforeAll
    @Commit
    public void setup() {

    }
}
