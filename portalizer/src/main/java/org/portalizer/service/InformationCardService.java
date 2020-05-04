package org.portalizer.service;

import org.portalizer.service.dto.InformationCardDTO;
import org.portalizer.service.dto.ReorderCardDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Validated
public interface InformationCardService {
    InformationCardDTO add(@Valid InformationCardDTO informationCardDTO);
    InformationCardDTO update(@Valid InformationCardDTO informationCardDTO);
    InformationCardDTO reorder(@Valid ReorderCardDTO reorderCardDTO);
    void delete(UUID cardId);
    InformationCardDTO merge(@Valid InformationCardDTO informationCardDTO);
}
