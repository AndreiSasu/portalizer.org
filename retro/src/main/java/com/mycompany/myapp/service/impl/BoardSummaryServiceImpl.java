package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BoardSummaryService;
import com.mycompany.myapp.domain.BoardSummary;
import com.mycompany.myapp.repository.BoardSummaryRepository;
import com.mycompany.myapp.service.dto.BoardSummaryDTO;
import com.mycompany.myapp.service.mapper.BoardSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link BoardSummary}.
 */
@Service
@Transactional
public class BoardSummaryServiceImpl implements BoardSummaryService {

    private final Logger log = LoggerFactory.getLogger(BoardSummaryServiceImpl.class);

    private final BoardSummaryRepository boardSummaryRepository;

    private final BoardSummaryMapper boardSummaryMapper;

    public BoardSummaryServiceImpl(BoardSummaryRepository boardSummaryRepository, BoardSummaryMapper boardSummaryMapper) {
        this.boardSummaryRepository = boardSummaryRepository;
        this.boardSummaryMapper = boardSummaryMapper;
    }

    /**
     * Save a boardSummary.
     *
     * @param boardSummaryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BoardSummaryDTO save(BoardSummaryDTO boardSummaryDTO) {
        log.debug("Request to save BoardSummary : {}", boardSummaryDTO);
        BoardSummary boardSummary = boardSummaryMapper.toEntity(boardSummaryDTO);
        boardSummary = boardSummaryRepository.save(boardSummary);
        return boardSummaryMapper.toDto(boardSummary);
    }

    /**
     * Get all the boardSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BoardSummaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BoardSummaries");
        return boardSummaryRepository.findAll(pageable)
            .map(boardSummaryMapper::toDto);
    }


    /**
     * Get one boardSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BoardSummaryDTO> findOne(UUID id) {
        log.debug("Request to get BoardSummary : {}", id);
        return boardSummaryRepository.findByBoardId(id)
            .map(boardSummaryMapper::toDto);
    }

    /**
     * Delete the boardSummary by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BoardSummary : {}", id);
        boardSummaryRepository.deleteById(id);
    }
}
