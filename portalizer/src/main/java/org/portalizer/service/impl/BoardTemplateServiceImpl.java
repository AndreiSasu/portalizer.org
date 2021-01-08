package org.portalizer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.portalizer.repository.BoardTemplateRepository;
import org.portalizer.service.BoardTemplateService;
import org.portalizer.service.dto.BoardTemplateDTO;
import org.portalizer.service.mapper.BoardTemplateMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BoardTemplateServiceImpl implements BoardTemplateService {

    private final BoardTemplateRepository boardTemplateRepository;
    private final BoardTemplateMapper boardTemplateMapper;

    public BoardTemplateServiceImpl(BoardTemplateRepository boardTemplateRepository, BoardTemplateMapper boardTemplateMapper) {
        this.boardTemplateRepository = boardTemplateRepository;
        this.boardTemplateMapper = boardTemplateMapper;
    }

    @Override
    public List<BoardTemplateDTO> getBoardTemplates() {
        return boardTemplateMapper.toDto(boardTemplateRepository.findAll());
    }
}
