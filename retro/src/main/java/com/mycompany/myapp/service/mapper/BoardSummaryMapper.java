package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BoardSummaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BoardSummary} and its DTO {@link BoardSummaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {BoardColumnMapper.class})
public interface BoardSummaryMapper extends EntityMapper<BoardSummaryDTO, BoardSummary> {


//    @Mapping(target = "removeBoardColumns", ignore = true)
    BoardSummary toEntity(BoardSummaryDTO boardSummaryDTO);

    default BoardSummary fromId(Long id) {
        if (id == null) {
            return null;
        }
        BoardSummary boardSummary = new BoardSummary();
        boardSummary.setId(id);
        return boardSummary;
    }
}
