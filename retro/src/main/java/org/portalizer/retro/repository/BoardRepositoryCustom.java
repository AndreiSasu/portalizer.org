package org.portalizer.retro.repository;

import org.portalizer.retro.domain.Board;

import java.util.Optional;
import java.util.UUID;

public interface BoardRepositoryCustom {
    Optional<Board> findFullBoardById(final UUID id);
}
