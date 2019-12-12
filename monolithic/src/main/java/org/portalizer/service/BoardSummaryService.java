package org.portalizer.service;

import org.portalizer.domain.BoardSummary;
import org.portalizer.service.dto.BoardSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link BoardSummary}.
 */
public interface BoardSummaryService {

    /**
     * Save a boardSummary.
     *
     * @param boardSummaryDTO the entity to save.
     * @return the persisted entity.
     */
    BoardSummaryDTO save(BoardSummaryDTO boardSummaryDTO);

    /**
     * Get all the boardSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BoardSummaryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" boardSummary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BoardSummaryDTO> findOne(UUID id);

    /**
     * Delete the "id" boardSummary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
