package org.portalizer.repository;

import org.portalizer.domain.InformationCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InformationCardRepository extends JpaRepository<InformationCard, UUID> {
    
    Optional<List<InformationCard>> findAllByBoardId(final UUID boardId);

    boolean existsByIdAndBoardId(final UUID id, final UUID boardId);

    Optional<InformationCard> findByIdAndBoardId(final UUID id, final UUID boardId);

    @Query("SELECT COUNT(c) FROM InformationCard c WHERE c.board.id=?1")
    long countByBoardId(final UUID boardId);

    Optional<List<InformationCard>> findAllByBoardIdAndColumnKey(final UUID boardId, final UUID columnKey);
}
