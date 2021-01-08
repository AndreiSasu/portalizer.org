package org.portalizer.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.portalizer.domain.BoardTemplate;
import org.portalizer.service.dto.BoardTemplateDTO;

@Mapper(componentModel = "spring", uses = {BoardTemplateColumnDefinitionMapper.class})
public interface BoardTemplateMapper extends EntityMapper<BoardTemplateDTO, BoardTemplate> {
    @Override
    @Mappings({@Mapping(source = "boardColumns", target = "columnDefinitions")})
    BoardTemplate toEntity(BoardTemplateDTO dto);

    @Override
    @Mappings({@Mapping(source = "columnDefinitions", target = "boardColumns")})
    BoardTemplateDTO toDto(BoardTemplate entity);
}
