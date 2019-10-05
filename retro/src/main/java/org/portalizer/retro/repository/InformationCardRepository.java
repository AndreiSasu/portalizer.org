package org.portalizer.retro.repository;

import org.portalizer.retro.domain.InformationCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InformationCardRepository extends JpaRepository<InformationCard, Long> {
    Optional<List<InformationCard>> findAllByBoardId(final UUID boardId);
}
