package org.portalizer.retro.repository;
import org.portalizer.retro.domain.BoardSummary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


/**
 * Spring Data  repository for the BoardSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoardSummaryRepository extends JpaRepository<BoardSummary, Long> {
    Optional<BoardSummary> findByBoardId(UUID boardId);
}
