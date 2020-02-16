package org.portalizer.service.impl;

import org.portalizer.domain.Board;
import org.portalizer.repository.BoardRepository;
import org.portalizer.repository.InformationCardRepository;
import org.portalizer.service.BoardService;
import org.portalizer.service.dto.*;
import org.portalizer.service.mapper.BoardMapper;
import org.portalizer.service.mapper.ColumnDefinitionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;
    private InformationCardRepository informationCardRepository;
    private BoardMapper boardMapper;
    private ColumnDefinitionMapper columnDefinitionMapper;

    public BoardServiceImpl(BoardRepository boardRepository, BoardMapper boardMapper, InformationCardRepository informationCardRepository, ColumnDefinitionMapper columnDefinitionMapper) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.columnDefinitionMapper = columnDefinitionMapper;
        this.informationCardRepository = informationCardRepository;
    }

    @Override
    public List<BoardProjectionDTO> search(String field, String search) {
        return boardRepository.searchFuzzy(field, search);
    }

    @Override
    public Page<BoardSummaryDTO> searchAll(String field, String search, Pageable pageable) {
        final Page<Board> boardPage = boardRepository.searchFuzzy(field, search, pageable);
        final List<Board> lazyBoards = boardPage.getContent();
        final List<BoardSummaryDTO> boardDTOS = lazyBoards.stream().map(this::lazyBoardToDto).collect(Collectors.toList());
        final Page<BoardSummaryDTO> boardDTOsPage = new PageImpl<>(boardDTOS, pageable, boardPage.getTotalElements());
        return boardDTOsPage;
    }

    @Override
    public Page<BoardSummaryDTO> findAll(final Pageable pageable) {
        final Page<Board> boardPage = boardRepository.findAll(pageable);
        final List<Board> lazyBoards = boardPage.getContent();
        final List<BoardSummaryDTO> boardDTOS = lazyBoards.stream().map(this::lazyBoardToDto).collect(Collectors.toList());
        final Page<BoardSummaryDTO> boardDTOsPage = new PageImpl<>(boardDTOS, pageable, boardPage.getTotalElements());
        return boardDTOsPage;
    }

    @Override
    public List<BoardSummaryDTO> findAll() {
        return boardRepository.findAll().stream().map(this::lazyBoardToDto).collect(Collectors.toList());
    }

    @Override
    public BoardDTO findOne(UUID id) {
        return boardRepository.findFullBoardById(id).map(boardMapper::toDto).get();
    }

    @Override
    public BoardDTO save(BoardDTO boardDTO) {
        Board saved = boardRepository.save(boardMapper.toEntity(boardDTO));
        return boardMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDTO update(UUID id, UpdateBoardDTO updateBoardDTO) {
        final Board board = boardRepository.findById(id).get();
        board.setName(updateBoardDTO.getName());
        boardRepository.save(board);
        final Board savedBoard = boardRepository.findById(id).get();
        return boardMapper.toDto(savedBoard);
    }

    private BoardSummaryDTO lazyBoardToDto(final Board board) {
        final BoardSummaryDTO boardDTO = new BoardSummaryDTO();
        boardDTO.setId(board.getId());
        boardDTO.setName(board.getName());
        boardDTO.setDescription(board.getDescription());
        boardDTO.setCreatedAt(board.getCreatedAt());
        boardDTO.setTotalCards(informationCardRepository.countByBoardId(board.getId()));
        List<ColumnDefinitionDTO> columnDefinitionDTOS = new ArrayList<>();
        board.getColumnDefinitions().forEach(columnDefinition -> {
            columnDefinitionDTOS.add(columnDefinitionMapper.toDto(columnDefinition));
        });
        boardDTO.setColumnDefinitions(columnDefinitionDTOS);
        return boardDTO;
    }
}
