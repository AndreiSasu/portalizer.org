package org.portalizer.web.rest;


import org.portalizer.service.BoardService;
import org.portalizer.service.dto.BoardProjectionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/retro/board-projections")
public class BoardProjectionResource {

    private BoardService boardService;

    public BoardProjectionResource(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<List<BoardProjectionDTO>> getBoardProjections(@RequestParam(required = true) final String fieldName, @RequestParam(required = true) final String search) {
        return new ResponseEntity<>(boardService.search(fieldName, search), HttpStatus.OK);
    }
}
