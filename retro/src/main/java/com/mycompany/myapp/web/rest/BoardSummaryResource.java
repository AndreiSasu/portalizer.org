package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.BoardSummaryService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.BoardSummaryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.BoardSummary}.
 */
@RestController
@RequestMapping("/api")
public class BoardSummaryResource {

    private final Logger log = LoggerFactory.getLogger(BoardSummaryResource.class);

    private static final String ENTITY_NAME = "retroBoardSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoardSummaryService boardSummaryService;

    public BoardSummaryResource(BoardSummaryService boardSummaryService) {
        this.boardSummaryService = boardSummaryService;
    }

    /**
     * {@code POST  /board-summaries} : Create a new boardSummary.
     *
     * @param boardSummaryDTO the boardSummaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boardSummaryDTO, or with status {@code 400 (Bad Request)} if the boardSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/board-summaries")
    public ResponseEntity<BoardSummaryDTO> createBoardSummary(@Valid @RequestBody BoardSummaryDTO boardSummaryDTO) throws URISyntaxException {
        log.debug("REST request to save BoardSummary : {}", boardSummaryDTO);
        if (boardSummaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new boardSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoardSummaryDTO result = boardSummaryService.save(boardSummaryDTO);
        return ResponseEntity.created(new URI("/api/board-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /board-summaries} : Updates an existing boardSummary.
     *
     * @param boardSummaryDTO the boardSummaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boardSummaryDTO,
     * or with status {@code 400 (Bad Request)} if the boardSummaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boardSummaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/board-summaries")
    public ResponseEntity<BoardSummaryDTO> updateBoardSummary(@Valid @RequestBody BoardSummaryDTO boardSummaryDTO) throws URISyntaxException {
        log.debug("REST request to update BoardSummary : {}", boardSummaryDTO);
        if (boardSummaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BoardSummaryDTO result = boardSummaryService.save(boardSummaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, boardSummaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /board-summaries} : get all the boardSummaries.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boardSummaries in body.
     */
    @GetMapping("/board-summaries")
    public ResponseEntity<List<BoardSummaryDTO>> getAllBoardSummaries(Pageable pageable) {
        log.debug("REST request to get a page of BoardSummaries");
        Page<BoardSummaryDTO> page = boardSummaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /board-summaries/:id} : get the "id" boardSummary.
     *
     * @param id the id of the boardSummaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boardSummaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/board-summaries/{id}")
    public ResponseEntity<BoardSummaryDTO> getBoardSummary(@PathVariable Long id) {
        log.debug("REST request to get BoardSummary : {}", id);
        Optional<BoardSummaryDTO> boardSummaryDTO = boardSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boardSummaryDTO);
    }

    /**
     * {@code DELETE  /board-summaries/:id} : delete the "id" boardSummary.
     *
     * @param id the id of the boardSummaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/board-summaries/{id}")
    public ResponseEntity<Void> deleteBoardSummary(@PathVariable Long id) {
        log.debug("REST request to delete BoardSummary : {}", id);
        boardSummaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
