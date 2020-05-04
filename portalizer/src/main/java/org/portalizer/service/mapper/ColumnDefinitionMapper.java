package org.portalizer.service.mapper;

import org.mapstruct.Mapper;
import org.portalizer.domain.ColumnDefinition;
import org.portalizer.service.dto.ColumnDefinitionDTO;

@Mapper(componentModel = "spring")
public interface ColumnDefinitionMapper extends EntityMapper<ColumnDefinitionDTO, ColumnDefinition> {

    @Override
    ColumnDefinition toEntity(ColumnDefinitionDTO dto);

    @Override
    ColumnDefinitionDTO toDto(ColumnDefinition entity);

}
