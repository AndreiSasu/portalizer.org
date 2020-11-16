package org.portalizer.web.rest;

import org.portalizer.service.BoardTemplateService;
import org.portalizer.service.dto.BoardTemplateDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/retro")
public class BoardTemplateResource {

    private final BoardTemplateService boardTemplateService;

    public BoardTemplateResource(BoardTemplateService boardTemplateService) {
        this.boardTemplateService = boardTemplateService;
    }

    @GetMapping("/board-templates")
    public List<BoardTemplateDTO> getBoardTemplates() {
        return this.boardTemplateService.getBoardTemplates();
    }
}
