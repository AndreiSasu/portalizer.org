package org.portalizer.web.rest;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.portalizer.domain.BoardSummary;
import org.portalizer.service.BoardSummaryService;
import org.portalizer.service.dto.BoardSummaryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing {@link BoardSummary}.
 */
@RestController
@RequestMapping("/api/retro")
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
     * @param boardId the id of the boardSummaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boardSummaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/board-summaries/{boardId}")
    public ResponseEntity<BoardSummaryDTO> getBoardSummary(@PathVariable UUID boardId) {
        log.debug("REST request to get BoardSummary : {}", boardId);
        Optional<BoardSummaryDTO> boardSummaryDTO = boardSummaryService.findOne(boardId);
        return ResponseUtil.wrapOrNotFound(boardSummaryDTO);
    }

}
