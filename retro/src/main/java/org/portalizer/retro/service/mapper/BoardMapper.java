package org.portalizer.retro.service.mapper;

import org.mapstruct.Mapper;
import org.portalizer.retro.domain.Board;
import org.portalizer.retro.service.dto.BoardDTO;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {ColumnDefinitionMapper.class, InformationCardMapper.class})
public interface BoardMapper extends EntityMapper<BoardDTO, Board> {

    @Override
    Board toEntity(BoardDTO dto);

    @Override
    BoardDTO toDto(Board entity);

    default Board fromId(UUID id) {
        if (id == null) {
            return null;
        }
        Board boardSummary = new Board();
        boardSummary.setId(id);
        return boardSummary;
    }
}
