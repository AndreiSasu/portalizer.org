package org.portalizer.web.rest;

import org.portalizer.service.InformationCardService;
import org.portalizer.service.dto.InformationCardDTO;
import org.springframework.http.HttpStatus;
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
        final InformationCardDTO savedCard = informationCardService.add(informationCardDTO);
        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
    }

    @PutMapping("/information-card")
    public ResponseEntity<InformationCardDTO> updateCard(@RequestBody InformationCardDTO informationCardDTO) {
        final InformationCardDTO updatedCard = informationCardService.update(informationCardDTO);
        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
    }

    @DeleteMapping("/information-card/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable UUID cardId) {
        informationCardService.delete(cardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
