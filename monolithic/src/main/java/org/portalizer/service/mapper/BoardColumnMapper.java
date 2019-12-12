package org.portalizer.service.mapper;

import org.mapstruct.Mapper;
import org.portalizer.domain.*;
import org.portalizer.domain.BoardColumn;
import org.portalizer.service.dto.BoardColumnDTO;

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
