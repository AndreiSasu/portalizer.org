package org.portalizer.service.impl;

import org.portalizer.domain.Board;
import org.portalizer.repository.BoardRepository;
import org.portalizer.service.BoardService;
import org.portalizer.service.dto.BoardDTO;
import org.portalizer.service.dto.ColumnDefinitionDTO;
import org.portalizer.service.mapper.BoardMapper;
import org.portalizer.service.mapper.ColumnDefinitionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;
    private BoardMapper boardMapper;
    private ColumnDefinitionMapper columnDefinitionMapper;

    public BoardServiceImpl(BoardRepository boardRepository, BoardMapper boardMapper, ColumnDefinitionMapper columnDefinitionMapper) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.columnDefinitionMapper = columnDefinitionMapper;
    }

    @Override
    public Page<BoardDTO> findAll(final Pageable pageable) {
        final Page<Board> boardPage = boardRepository.findAll(pageable);
        final List<Board> lazyBoards = boardPage.getContent();
        final List<BoardDTO> boardDTOS = lazyBoards.stream().map(this::lazyBoardToDto).collect(Collectors.toList());
        final Page<BoardDTO> boardDTOsPage = new PageImpl<>(boardDTOS, pageable, boardPage.getTotalElements());
        return boardDTOsPage;
    }

    @Override
    public List<BoardDTO> findAll() {
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

    private BoardDTO lazyBoardToDto(final Board board) {
        final BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setName(board.getName());
        boardDTO.setDescription(board.getDescription());
        boardDTO.setCreatedAt(board.getCreatedAt());
        List<ColumnDefinitionDTO> columnDefinitionDTOS = new ArrayList<>();
        board.getColumnDefinitions().forEach(columnDefinition -> {
            columnDefinitionDTOS.add(columnDefinitionMapper.toDto(columnDefinition));
        });
        boardDTO.setColumnDefinitions(columnDefinitionDTOS);
        return boardDTO;
    }
}
