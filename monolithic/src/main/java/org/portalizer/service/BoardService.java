package org.portalizer.service;

import org.portalizer.service.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BoardService {

    Page<BoardDTO> findAll(Pageable pageable);
    List<BoardDTO> findAll();
    BoardDTO findOne(UUID id);
    BoardDTO save(BoardDTO boardDTO);
    void delete(UUID id);
}
