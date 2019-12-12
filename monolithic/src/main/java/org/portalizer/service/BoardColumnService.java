package org.portalizer.service;

import org.portalizer.domain.BoardColumn;
import org.portalizer.service.dto.BoardColumnDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BoardColumn}.
 */
public interface BoardColumnService {

    /**
     * Save a boardColumn.
     *
     * @param boardColumnDTO the entity to save.
     * @return the persisted entity.
     */
    BoardColumnDTO save(BoardColumnDTO boardColumnDTO);

    /**
     * Get all the boardColumns.
     *
     * @return the list of entities.
     */
    List<BoardColumnDTO> findAll();


    /**
     * Get the "id" boardColumn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BoardColumnDTO> findOne(Long id);

    /**
     * Delete the "id" boardColumn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
