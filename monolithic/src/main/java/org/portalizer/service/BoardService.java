package org.portalizer.service;

import org.portalizer.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BoardService {

    List<BoardProjectionDTO> search(final String field, final String search);
    Page<BoardSummaryDTO> searchAll(final String field, final String search, final Pageable pageable);
    Page<BoardSummaryDTO> findAll(Pageable pageable);
    List<BoardSummaryDTO> findAll();
    BoardDTO findOne(UUID id);
    BoardDTO save(BoardDTO boardDTO);
    void delete(UUID id);
    BoardDTO update(UUID id, UpdateBoardDTO updateBoardDTO);
    BoardDTO reorderColumns(UUID id, ReorderColumnsDTO reorderColumnsDTO);
}
