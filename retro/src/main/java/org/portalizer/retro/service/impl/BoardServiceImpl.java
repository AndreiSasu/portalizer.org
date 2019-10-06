package org.portalizer.retro.service.impl;

import org.portalizer.retro.repository.BoardRepository;
import org.portalizer.retro.service.BoardService;
import org.portalizer.retro.service.dto.BoardDTO;
import org.portalizer.retro.service.dto.ColumnDefinitionDTO;
import org.portalizer.retro.service.mapper.BoardMapper;
import org.portalizer.retro.service.mapper.ColumnDefinitionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
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
    public List<BoardDTO> findAll() {
        return boardRepository.findAll().stream().map(board -> {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(board.getId());
            SortedSet<ColumnDefinitionDTO> columnDefinitionDTOS = new TreeSet<>();
            board.getColumnDefinitions().forEach(columnDefinition -> {
                columnDefinitionDTOS.add(columnDefinitionMapper.toDto(columnDefinition));
            });
            boardDTO.setColumnDefinitions(columnDefinitionDTOS);
            return boardDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public BoardDTO findOne(UUID id) {
        return boardRepository.findFullBoardById(id).map(boardMapper::toDto).get();
    }
}
