package org.portalizer.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.portalizer.service.BoardService;
import org.portalizer.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/retro")
public class BoardResource {
    private final Logger log = LoggerFactory.getLogger(BoardResource.class);

    private BoardService boardService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public BoardResource(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public ResponseEntity<Page<BoardSummaryDTO>> getAllBoards(
        @RequestParam(required = false) final String searchField, @RequestParam(required = false) final String searchPhrase,
        @PageableDefault(size = 25, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return StringUtils.isNotEmpty(searchField) && StringUtils.isNotEmpty(searchPhrase) ?
            new ResponseEntity<>(boardService.searchAll(searchField, searchPhrase, pageable), HttpStatus.OK) :
            new ResponseEntity<>(boardService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable UUID id) {
        return new ResponseEntity<>(boardService.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/boards")
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO) {
        return new ResponseEntity<>(boardService.save(boardDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable UUID id) {
        boardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert("",
            false,
            "board", id.toString())).build();
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable UUID id, @RequestBody UpdateBoardDTO updateBoardDTO) {
        boardService.update(id, updateBoardDTO);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert("",
            false,
            "board", id.toString())).build();
    }

    @PutMapping("/boards/{id}/columns")
    public ResponseEntity<Void> reorderColumns(@PathVariable UUID id, @RequestBody ReorderColumnsDTO reorderColumnsDTO) {
        final BoardDTO boardDTO = boardService.reorderColumns(id, reorderColumnsDTO);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert("",
            false,
            "board", boardDTO.getId().toString())).build();
    }

    @PostMapping("/boards/{id}/columns")
    public ResponseEntity<ColumnDefinitionDTO> addColumn(@PathVariable UUID id, @RequestBody AddColumnDTO addColumnDTO) {
        final ColumnDefinitionDTO columnDefinitionDTO = boardService.addColumn(id, addColumnDTO);
        return new ResponseEntity<>(columnDefinitionDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/boards/{boardId}/columns/{columnKey}")
    public ResponseEntity<Void> deleteColumn(@PathVariable UUID boardId, @PathVariable UUID columnKey) {
        boardService.removeColumn(boardId, columnKey);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert("",
            false,
            "column", columnKey.toString())).build();
    }

}
