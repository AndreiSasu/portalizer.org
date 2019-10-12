package org.portalizer.retro.service;

import org.portalizer.retro.service.dto.InformationCardDTO;

public interface InformationCardService {
    InformationCardDTO add(InformationCardDTO informationCardDTO);
    void delete();
    InformationCardDTO merge(InformationCardDTO informationCardDTO);
}
