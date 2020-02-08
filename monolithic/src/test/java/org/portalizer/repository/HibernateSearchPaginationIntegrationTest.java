package org.portalizer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.portalizer.PortalizerApp;
import org.portalizer.domain.Board;
import org.portalizer.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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
        boardRepository.deleteAll();
        boards = new ArrayList<>();

        for(int i = 0; i < 25; i ++) {
            for(int x = 0; x < 5; x++) {
                boards.add(EntityUtils.validBoard("Apple iPhone X 256 GB " + i, "The current high-end smartphone from Apple, with lots of memory and also Face ID"));
            }
        }

        boardRepository.saveAll(boards);
    }

    @Test
    public void testPagination() {

        for(int i = 0; i < 25; i++) {
            final Page<Board> boardsPage = boardRepository.searchFuzzy("name", "iPhone", PageRequest.of(i, 5));
            final List<Board> content = boardsPage.getContent();

            Assertions.assertThat(boardsPage.getTotalElements()).isEqualTo(125);
            Assertions.assertThat(boardsPage.getNumber()).isEqualTo(i);
            int finalI = i;
            Assertions.assertThat(content).hasSize(5).allSatisfy(board -> {
                Assertions.assertThat(board.getName()).isEqualTo("Apple iPhone X 256 GB "+ finalI);
            });

        }


    }
}
