package org.portalizer.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.portalizer.domain.*;
import org.portalizer.domain.BoardSummary;
import org.portalizer.service.dto.BoardSummaryDTO;

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
