package org.portalizer.service;

import org.portalizer.service.dto.AddCardDTO;
import org.portalizer.service.dto.InformationCardDTO;
import org.portalizer.service.dto.ReorderCardDTO;
import org.portalizer.service.dto.UpdateCardDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Validated
public interface InformationCardService {
    InformationCardDTO add(@Valid AddCardDTO addCardDTO);
    InformationCardDTO update(final UUID cardId, @Valid UpdateCardDTO updateCardDTO);
    InformationCardDTO reorder(final UUID cardId, @Valid ReorderCardDTO reorderCardDTO);
    void delete(UUID cardId);
    InformationCardDTO merge(@Valid InformationCardDTO informationCardDTO);
}
