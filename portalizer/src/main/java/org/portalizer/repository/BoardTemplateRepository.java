package org.portalizer.repository;

import org.portalizer.domain.BoardTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardTemplateRepository extends JpaRepository<BoardTemplate, UUID> {

    boolean existsByTitle(final String title);
    boolean existsByBoardId(final UUID boardId);

}
