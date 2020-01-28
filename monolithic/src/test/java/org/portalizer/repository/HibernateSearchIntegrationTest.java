package org.portalizer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.portalizer.PortalizerApp;
import org.portalizer.domain.Board;
import org.portalizer.service.dto.BoardProjectionDTO;
import org.portalizer.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest(classes = PortalizerApp.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HibernateSearchIntegrationTest {


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

        boards.add(EntityUtils.validBoard("Apple iPhone X 256 GB", "The current high-end smartphone from Apple, with lots of memory and also Face ID"));
        boards.add(EntityUtils.validBoard("Apple iPhone X 128 GB", "The current high-end smartphone from Apple, with Face ID"));
        boards.add(EntityUtils.validBoard("Apple iPhone 8 128 GB", "The latest smartphone from Apple within the regular iPhone line, supporting wireless charging"));
        boards.add(EntityUtils.validBoard("Samsung Galaxy S7 128 GB", "A great Android smartphone"));
        boards.add(EntityUtils.validBoard("Microsoft Lumia 650 32 GB", "A cheaper smartphone, coming with Windows Mobile"));
        boards.add(EntityUtils.validBoard("Microsoft Lumia 640 32 GB", "A cheaper smartphone, coming with Windows Mobile"));
        boards.add(EntityUtils.validBoard("Microsoft Lumia 630 16 GB", "A cheaper smartphone, coming with Windows Mobile"));

        boardRepository.saveAll(boards);
    }


    @Test
    public void testSearchBoardByDescription() {
        final Board expected = boards.get(2);
        final UUID expectedIds = expected.getId();
        final List<BoardProjectionDTO> actual = boardRepository.searchPhrase("description", "with wireless charging");
        final List<UUID> actualIds = actual.stream().map(BoardProjectionDTO::getId).collect(Collectors.toList());
        Assertions.assertThat(actualIds).hasSize(1)
            .containsExactlyInAnyOrder(expectedIds);
    }


    @Test
    public void testSearchBoardByName() {
        final List<Board> expected = boards.subList(0, 3);
        final List<UUID> expectedIds = expected.stream().map(Board::getId).collect(Collectors.toList());
        final List<BoardProjectionDTO> actual = boardRepository.searchFuzzy("name", "iPhone");
        final List<UUID> actualIds = actual.stream().map(BoardProjectionDTO::getId).collect(Collectors.toList());
        Assertions.assertThat(actualIds).hasSize(3)
            .containsExactlyInAnyOrder(expectedIds.get(0), expectedIds.get(1), expectedIds.get(2));

    }
}
