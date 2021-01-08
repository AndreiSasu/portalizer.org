package org.portalizer.service.mapper;

import org.mapstruct.Mapper;
import org.portalizer.domain.BoardTemplateColumnDefinition;
import org.portalizer.service.dto.BoardTemplateColumnDefinitionDTO;

@Mapper(componentModel = "spring")
public interface BoardTemplateColumnDefinitionMapper extends EntityMapper<BoardTemplateColumnDefinitionDTO, BoardTemplateColumnDefinition> {
}
