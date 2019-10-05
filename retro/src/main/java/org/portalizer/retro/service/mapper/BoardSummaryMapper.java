package org.portalizer.retro.service.mapper;

import org.portalizer.retro.domain.*;
import org.portalizer.retro.service.dto.BoardSummaryDTO;

import org.mapstruct.*;
import org.portalizer.retro.domain.BoardSummary;

/**
 * Mapper for the entity {@link BoardSummary} and its DTO {@link BoardSummaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {BoardColumnMapper.class})
public interface BoardSummaryMapper extends EntityMapper<BoardSummaryDTO, BoardSummary> {

    @Mappings({@Mapping(source = "boardId", target = "boardId", ignore = true)})
    BoardSummary toEntity(BoardSummaryDTO boardSummaryDTO);

    @Mappings({@Mapping(source = "boardId", target = "boardId")})
    BoardSummaryDTO toDto(BoardSummary boardSummaryDTO);

    default BoardSummary fromId(Long id) {
        if (id == null) {
            return null;
        }
        BoardSummary boardSummary = new BoardSummary();
        boardSummary.setId(id);
        return boardSummary;
    }
}
