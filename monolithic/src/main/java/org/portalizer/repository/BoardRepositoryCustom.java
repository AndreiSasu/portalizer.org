package org.portalizer.repository;

import org.portalizer.domain.Board;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepositoryCustom {
    Optional<Board> findFullBoardById(final UUID id);

    List<Board> searchFuzzy(final String fieldName, final String searchText);

    List<Board> searchWildCard(final String fieldName, final String searchText);

    List<Board> searchPhrase(final String fieldName, final String searchText);
}
