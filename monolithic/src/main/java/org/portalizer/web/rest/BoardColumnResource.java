package org.portalizer.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.portalizer.domain.BoardColumn;
import org.portalizer.service.BoardColumnService;
import org.portalizer.service.dto.BoardColumnDTO;
import org.portalizer.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link BoardColumn}.
 */
@RestController
@RequestMapping("/api/retro")
public class BoardColumnResource {

    private final Logger log = LoggerFactory.getLogger(BoardColumnResource.class);

    private static final String ENTITY_NAME = "retroBoardColumn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoardColumnService boardColumnService;

    public BoardColumnResource(BoardColumnService boardColumnService) {
        this.boardColumnService = boardColumnService;
    }

    /**
     * {@code POST  /board-columns} : Create a new boardColumn.
     *
     * @param boardColumnDTO the boardColumnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boardColumnDTO, or with status {@code 400 (Bad Request)} if the boardColumn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/board-columns")
    public ResponseEntity<BoardColumnDTO> createBoardColumn(@Valid @RequestBody BoardColumnDTO boardColumnDTO) throws URISyntaxException {
        log.debug("REST request to save BoardColumn : {}", boardColumnDTO);
        if (boardColumnDTO.getId() != null) {
            throw new BadRequestAlertException("A new boardColumn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoardColumnDTO result = boardColumnService.save(boardColumnDTO);
        return ResponseEntity.created(new URI("/api/board-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /board-columns} : Updates an existing boardColumn.
     *
     * @param boardColumnDTO the boardColumnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boardColumnDTO,
     * or with status {@code 400 (Bad Request)} if the boardColumnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boardColumnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/board-columns")
    public ResponseEntity<BoardColumnDTO> updateBoardColumn(@Valid @RequestBody BoardColumnDTO boardColumnDTO) throws URISyntaxException {
        log.debug("REST request to update BoardColumn : {}", boardColumnDTO);
        if (boardColumnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BoardColumnDTO result = boardColumnService.save(boardColumnDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, boardColumnDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /board-columns} : get all the boardColumns.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boardColumns in body.
     */
    @GetMapping("/board-columns")
    public List<BoardColumnDTO> getAllBoardColumns() {
        log.debug("REST request to get all BoardColumns");
        return boardColumnService.findAll();
    }

    /**
     * {@code GET  /board-columns/:id} : get the "id" boardColumn.
     *
     * @param id the id of the boardColumnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boardColumnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/board-columns/{id}")
    public ResponseEntity<BoardColumnDTO> getBoardColumn(@PathVariable Long id) {
        log.debug("REST request to get BoardColumn : {}", id);
        Optional<BoardColumnDTO> boardColumnDTO = boardColumnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boardColumnDTO);
    }

    /**
     * {@code DELETE  /board-columns/:id} : delete the "id" boardColumn.
     *
     * @param id the id of the boardColumnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/board-columns/{id}")
    public ResponseEntity<Void> deleteBoardColumn(@PathVariable Long id) {
        log.debug("REST request to delete BoardColumn : {}", id);
        boardColumnService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
