package org.portalizer.service;

import org.portalizer.service.dto.BoardDTO;
import org.portalizer.service.dto.BoardProjectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BoardService {

    List<BoardProjectionDTO> search(final String field, final String search);
    Page<BoardDTO> searchAll(final String field, final String search, final Pageable pageable);
    Page<BoardDTO> findAll(Pageable pageable);
    List<BoardDTO> findAll();
    BoardDTO findOne(UUID id);
    BoardDTO save(BoardDTO boardDTO);
    void delete(UUID id);
}
