package org.portalizer.repository;

import org.portalizer.domain.Board;

import java.util.Optional;
import java.util.UUID;

public interface BoardRepositoryCustom {
    Optional<Board> findFullBoardById(final UUID id);
}
