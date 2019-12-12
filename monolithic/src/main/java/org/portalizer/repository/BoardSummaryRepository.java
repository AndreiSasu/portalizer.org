package org.portalizer.repository;

import org.portalizer.domain.BoardSummary;
import org.springframework.data.jpa.repository.JpaRepository;
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
