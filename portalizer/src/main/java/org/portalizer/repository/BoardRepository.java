package org.portalizer.repository;

import org.portalizer.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID>, BoardRepositoryCustom {
    List<Board> findBoardsByOwnerId(final Long id);
}
