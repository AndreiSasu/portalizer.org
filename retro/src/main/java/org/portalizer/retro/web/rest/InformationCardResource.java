package org.portalizer.retro.web.rest;

import org.portalizer.retro.service.InformationCardService;
import org.portalizer.retro.service.dto.InformationCardDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class InformationCardResource {

    final InformationCardService informationCardService;

    public InformationCardResource(InformationCardService informationCardService) {
        this.informationCardService = informationCardService;
    }

    @PostMapping("/information-card")
    public ResponseEntity<InformationCardDTO> addCard(@RequestBody InformationCardDTO informationCardDTO) {
        return null;
    }

    @PutMapping("/information-card")
    public ResponseEntity<InformationCardDTO> updateCard(@RequestBody InformationCardDTO informationCardDTO) {
        return null;
    }

    @DeleteMapping("/information-card/{cardId}")
    public ResponseEntity<InformationCardDTO> deleteCard(@PathVariable UUID cardId) {
        return null;
    }
}
