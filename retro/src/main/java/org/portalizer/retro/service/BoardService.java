package org.portalizer.retro.service;

import org.portalizer.retro.service.dto.BoardDTO;

import java.util.List;
import java.util.UUID;

public interface BoardService {

    List<BoardDTO> findAll();
    BoardDTO findOne(UUID id);
}
