package org.portalizer.service.mapper;

import org.mapstruct.Mapper;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.service.dto.BoardTemplateColumnDefinitionDTO;
@Mapper(componentModel = "spring")
public interface ColumnDefinitionTemplateMapper extends EntityMapper<BoardTemplateColumnDefinitionDTO, ColumnDefinition> {
}
