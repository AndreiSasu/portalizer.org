package org.portalizer.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.portalizer.domain.InformationCard;
import org.portalizer.service.dto.InformationCardDTO;

@Mapper(componentModel = "spring")
public interface InformationCardMapper extends EntityMapper<InformationCardDTO, InformationCard> {

    @Override
    @Mappings({@Mapping(source = "createdAt", target = "createdAt", ignore = true),
        @Mapping(source = "updatedAt", target = "updatedAt", ignore = true)})
    InformationCard toEntity(InformationCardDTO dto);

    @Override
    @Mappings({@Mapping(source = "board.id", target = "boardId")})
    InformationCardDTO toDto(InformationCard entity);
}
