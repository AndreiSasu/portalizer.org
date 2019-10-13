package org.portalizer.retro.service;

import org.portalizer.retro.service.dto.InformationCardDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Validated
public interface InformationCardService {
    InformationCardDTO add(@Valid InformationCardDTO informationCardDTO);
    InformationCardDTO update(@Valid InformationCardDTO informationCardDTO);
    void delete(UUID cardId);
    InformationCardDTO merge(@Valid InformationCardDTO informationCardDTO);
}
