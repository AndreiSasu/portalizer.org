package org.portalizer.retro.service.mapper;

import org.portalizer.retro.domain.*;
import org.portalizer.retro.domain.BoardColumn;
import org.portalizer.retro.service.dto.BoardColumnDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BoardColumn} and its DTO {@link BoardColumnDTO}.
 */
@Mapper(componentModel = "spring", uses = {BoardSummaryMapper.class})
public interface BoardColumnMapper extends EntityMapper<BoardColumnDTO, BoardColumn> {

    BoardColumnDTO toDto(BoardColumn boardColumn);

    BoardColumn toEntity(BoardColumnDTO boardColumnDTO);

    default BoardColumn fromId(Long id) {
        if (id == null) {
            return null;
        }
        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setId(id);
        return boardColumn;
    }
}
