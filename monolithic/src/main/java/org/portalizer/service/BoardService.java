package org.portalizer.service;

import org.portalizer.service.dto.BoardDTO;

import java.util.List;
import java.util.UUID;

public interface BoardService {

    List<BoardDTO> findAll();
    BoardDTO findOne(UUID id);
    BoardDTO save(BoardDTO boardDTO);
    void delete(UUID id);
}
