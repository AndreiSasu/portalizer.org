package org.portalizer.retro.service.mapper;

import org.mapstruct.Mapper;
import org.portalizer.retro.domain.InformationCard;
import org.portalizer.retro.service.dto.InformationCardDTO;

@Mapper(componentModel = "spring")
public interface InformationCardMapper extends EntityMapper<InformationCardDTO, InformationCard> {

    @Override
    InformationCard toEntity(InformationCardDTO dto);

    @Override
    InformationCardDTO toDto(InformationCard entity);
}
