package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BoardSummaryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.BoardSummary}.
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
    Optional<BoardSummaryDTO> findOne(Long id);

    /**
     * Delete the "id" boardSummary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
