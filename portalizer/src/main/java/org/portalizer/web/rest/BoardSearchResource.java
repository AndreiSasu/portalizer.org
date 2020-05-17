package org.portalizer.web.rest;


import org.portalizer.service.BoardService;
import org.portalizer.service.dto.BoardDTO;
import org.portalizer.service.dto.BoardProjectionDTO;
import org.portalizer.service.dto.BoardSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/retro/boards/search")
public class BoardSearchResource {

    private BoardService boardService;

    public BoardSearchResource(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/light")
    public ResponseEntity<List<BoardProjectionDTO>> getBoardProjections(@RequestParam(required = true) final String fieldName, @RequestParam(required = true) final String search) {
        return new ResponseEntity<>(boardService.search(fieldName, search), HttpStatus.OK);
    }
}
