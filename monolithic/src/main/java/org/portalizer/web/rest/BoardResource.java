package org.portalizer.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import org.portalizer.service.BoardService;
import org.portalizer.service.dto.BoardDTO;
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

import java.util.List;
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
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        return new ResponseEntity<>(boardService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/boards-paged")
    public ResponseEntity<Page<BoardDTO>> getAllBoardsPaged(@PageableDefault(size = 25, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(boardService.findAll(pageable), HttpStatus.OK);
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
}
