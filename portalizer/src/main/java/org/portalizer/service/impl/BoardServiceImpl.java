package org.portalizer.service.impl;

import org.portalizer.domain.Board;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.domain.User;
import org.portalizer.repository.BoardRepository;
import org.portalizer.repository.ColumnDefinitionRepository;
import org.portalizer.repository.InformationCardRepository;
import org.portalizer.service.BoardService;
import org.portalizer.service.UserService;
import org.portalizer.service.dto.*;
import org.portalizer.service.mapper.BoardMapper;
import org.portalizer.service.mapper.ColumnDefinitionMapper;
import org.portalizer.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final InformationCardRepository informationCardRepository;
    private final BoardMapper boardMapper;
    private final UserMapper userMapper;
    private final ColumnDefinitionMapper columnDefinitionMapper;
    private final ColumnDefinitionRepository columnDefinitionRepository;
    private final UserService userService;

    public BoardServiceImpl(BoardRepository boardRepository,
                            BoardMapper boardMapper,
                            ColumnDefinitionRepository columnDefinitionRepository,
                            InformationCardRepository informationCardRepository,
                            ColumnDefinitionMapper columnDefinitionMapper,
                            UserMapper userMapper,
                            UserService userService) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.columnDefinitionMapper = columnDefinitionMapper;
        this.informationCardRepository = informationCardRepository;
        this.columnDefinitionRepository = columnDefinitionRepository;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public List<BoardProjectionDTO> search(String field, String search) {
        return boardRepository.searchFuzzy(field, search);
    }

    @Override
    public Page<BoardSummaryDTO> searchAll(String field, String search, Pageable pageable) {
        final Page<Board> boardPage = boardRepository.searchFuzzy(field, search, pageable);
        final List<Board> lazyBoards = boardPage.getContent();
        final List<BoardSummaryDTO> boardDTOS = lazyBoards.stream().map(this::lazyBoardToDto)
            .collect(Collectors.toList());
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
        final Board toSave = boardMapper.toEntity(boardDTO);
        final Optional<User> maybeLoggedInUser = userService.getUserWithAuthorities();
        if(maybeLoggedInUser.isPresent()) {
            toSave.setOwner(maybeLoggedInUser.get());
        }
        toSave.getColumnDefinitions().forEach(columnDefinition -> {
            columnDefinition.setBoard(toSave);
        });
        Board saved = boardRepository.save(toSave);
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

    @Override
    public BoardDTO reorderColumns(UUID id, ReorderColumnsDTO reorderColumnsDTO) {
        final Board board = boardRepository.findById(id).get();
        final List<ColumnDefinition> columnDefinitions = board.getColumnDefinitions();
        final int oldIndex = reorderColumnsDTO.getOldIndex();
        final int newIndex = reorderColumnsDTO.getNewIndex();
        final ColumnDefinition columnDefinition = columnDefinitions.remove(oldIndex);
        columnDefinitions.add(newIndex, columnDefinition);
        for (int i = 0; i < columnDefinitions.size(); i++) {
            columnDefinitions.get(i).setPriority(i);
        }
        return boardMapper.toDto(boardRepository.save(board));
    }

    @Override
    public ColumnDefinitionDTO addColumn(UUID id, AddColumnDTO addColumnDTO) {
        final Board board = boardRepository.findById(id).get();
        final List<ColumnDefinition> columnDefinitions = board.getColumnDefinitions();
        final ColumnDefinition newColumn = new ColumnDefinition();
        newColumn.setBoard(board);
        newColumn.setTitle(addColumnDTO.getTitle());
        newColumn.setKey(UUID.randomUUID());
        newColumn.setPriority(columnDefinitions.size());
        columnDefinitions.add(newColumn);
        final Board savedBoard = boardRepository.save(board);
        final ColumnDefinition savedColumnDefinition = savedBoard.getColumnDefinitions().stream().filter(columnDefinition -> columnDefinition.getKey().equals(newColumn.getKey())).findFirst().get();
        return columnDefinitionMapper.toDto(savedColumnDefinition);
    }

    @Override
    public void removeColumn(UUID boardId, UUID columnKey) {
        final Optional<Board> board = boardRepository.findById(boardId);
        if(board.isPresent()) {
            final List<ColumnDefinition> columnDefinitions = board.get().getColumnDefinitions();
            final List<ColumnDefinition> filtered = columnDefinitions.stream().filter(columnDefinition -> columnKey.equals(columnDefinition.getKey())).collect(Collectors.toList());
            filtered.forEach(columnDefinition -> {
                columnDefinitionRepository.deleteById(columnDefinition.getId());
                columnDefinitions.remove(columnDefinition);
            });
            boardRepository.save(board.get());
            informationCardRepository.deleteAllByBoardIdAndColumnKey(board.get().getId(), columnKey);
        }
    }

    private BoardSummaryDTO lazyBoardToDto(final Board board) {
        final BoardSummaryDTO boardDTO = new BoardSummaryDTO();
        boardDTO.setId(board.getId());
        boardDTO.setName(board.getName());
        boardDTO.setDescription(board.getDescription());
        boardDTO.setCreatedAt(board.getCreatedAt());
        boardDTO.setOwner(userMapper.toDto(board.getOwner()));
        boardDTO.setTotalCards(informationCardRepository.countByBoardId(board.getId()));
        List<ColumnDefinitionDTO> columnDefinitionDTOS = new ArrayList<>();
        board.getColumnDefinitions().forEach(columnDefinition -> {
            columnDefinitionDTOS.add(columnDefinitionMapper.toDto(columnDefinition));
        });
        boardDTO.setColumnDefinitions(columnDefinitionDTOS);
        return boardDTO;
    }

}
