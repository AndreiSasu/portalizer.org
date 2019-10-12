package org.portalizer.retro.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import org.portalizer.retro.service.BoardService;
import org.portalizer.retro.service.dto.BoardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable UUID id) {
        return new ResponseEntity<>(boardService.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/boards")
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO) {
        return new ResponseEntity<>(boardService.save(boardDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> createBoard(@PathVariable UUID id) {
        boardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert("",
            false,
            "board", id.toString())).build();
    }
}