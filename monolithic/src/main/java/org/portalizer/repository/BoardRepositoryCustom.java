package org.portalizer.repository;

import org.portalizer.domain.Board;
import org.portalizer.service.dto.BoardProjectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepositoryCustom {
    Optional<Board> findFullBoardById(final UUID id);

    List<BoardProjectionDTO> searchFuzzy(final String fieldName, final String searchText);

    List<BoardProjectionDTO> searchWildCard(final String fieldName, final String searchText);

    List<BoardProjectionDTO> searchPhrase(final String fieldName, final String searchText);

    Page<Board> searchFuzzy(final String fieldName, final String searchText, final Pageable pageable);
}
