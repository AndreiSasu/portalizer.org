package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BoardColumnDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BoardColumn} and its DTO {@link BoardColumnDTO}.
 */
@Mapper(componentModel = "spring", uses = {BoardSummaryMapper.class})
public interface BoardColumnMapper extends EntityMapper<BoardColumnDTO, BoardColumn> {

    @Mapping(source = "boardSummary.id", target = "boardSummaryId")
    BoardColumnDTO toDto(BoardColumn boardColumn);

    @Mapping(source = "boardSummaryId", target = "boardSummary")
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
