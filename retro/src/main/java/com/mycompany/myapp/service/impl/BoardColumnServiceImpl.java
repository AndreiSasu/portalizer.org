package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BoardColumnService;
import com.mycompany.myapp.domain.BoardColumn;
import com.mycompany.myapp.repository.BoardColumnRepository;
import com.mycompany.myapp.service.dto.BoardColumnDTO;
import com.mycompany.myapp.service.mapper.BoardColumnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BoardColumn}.
 */
@Service
@Transactional
public class BoardColumnServiceImpl implements BoardColumnService {

    private final Logger log = LoggerFactory.getLogger(BoardColumnServiceImpl.class);

    private final BoardColumnRepository boardColumnRepository;

    private final BoardColumnMapper boardColumnMapper;

    public BoardColumnServiceImpl(BoardColumnRepository boardColumnRepository, BoardColumnMapper boardColumnMapper) {
        this.boardColumnRepository = boardColumnRepository;
        this.boardColumnMapper = boardColumnMapper;
    }

    /**
     * Save a boardColumn.
     *
     * @param boardColumnDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BoardColumnDTO save(BoardColumnDTO boardColumnDTO) {
        log.debug("Request to save BoardColumn : {}", boardColumnDTO);
        BoardColumn boardColumn = boardColumnMapper.toEntity(boardColumnDTO);
        boardColumn = boardColumnRepository.save(boardColumn);
        return boardColumnMapper.toDto(boardColumn);
    }

    /**
     * Get all the boardColumns.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BoardColumnDTO> findAll() {
        log.debug("Request to get all BoardColumns");
        return boardColumnRepository.findAll().stream()
            .map(boardColumnMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one boardColumn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BoardColumnDTO> findOne(Long id) {
        log.debug("Request to get BoardColumn : {}", id);
        return boardColumnRepository.findById(id)
            .map(boardColumnMapper::toDto);
    }

    /**
     * Delete the boardColumn by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BoardColumn : {}", id);
        boardColumnRepository.deleteById(id);
    }
}
