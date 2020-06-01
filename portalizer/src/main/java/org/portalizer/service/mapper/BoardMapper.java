package org.portalizer.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.portalizer.domain.Board;
import org.portalizer.service.dto.BoardDTO;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {ColumnDefinitionMapper.class, UserMapper.class, InformationCardMapper.class})
public interface BoardMapper extends EntityMapper<BoardDTO, Board> {

    @Override
    @Mappings({@Mapping(source = "createdAt", target = "createdAt", ignore = true),
        @Mapping(source = "id", target = "id", ignore = true)})
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
