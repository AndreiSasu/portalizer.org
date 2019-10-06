package org.portalizer.retro.service.mapper;

import org.mapstruct.Mapper;
import org.portalizer.retro.domain.ColumnDefinition;
import org.portalizer.retro.service.dto.ColumnDefinitionDTO;

@Mapper(componentModel = "spring")
public interface ColumnDefinitionMapper extends EntityMapper<ColumnDefinitionDTO, ColumnDefinition> {

    @Override
    ColumnDefinition toEntity(ColumnDefinitionDTO dto);

    @Override
    ColumnDefinitionDTO toDto(ColumnDefinition entity);

}
