package org.portalizer.retro.web.rest;

import org.portalizer.retro.service.BoardService;
import org.portalizer.retro.service.dto.BoardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BoardResource {
    private final Logger log = LoggerFactory.getLogger(BoardResource.class);

    private BoardService boardService;

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
}
