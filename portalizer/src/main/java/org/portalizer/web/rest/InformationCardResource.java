package org.portalizer.web.rest;

import org.portalizer.service.InformationCardService;
import org.portalizer.service.dto.AddCardDTO;
import org.portalizer.service.dto.InformationCardDTO;
import org.portalizer.service.dto.ReorderCardDTO;
import org.portalizer.service.dto.UpdateCardDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/retro")
public class InformationCardResource {

    final InformationCardService informationCardService;

    public InformationCardResource(InformationCardService informationCardService) {
        this.informationCardService = informationCardService;
    }

    @PutMapping("/cards/{cardId}/reorder")
    public ResponseEntity<InformationCardDTO> reorder(@PathVariable UUID cardId, @RequestBody ReorderCardDTO reorderCardDTO) {
        final InformationCardDTO savedCard = informationCardService.reorder(cardId, reorderCardDTO);
        return new ResponseEntity<>(savedCard, HttpStatus.OK);
    }

    @PostMapping("/cards")
    public ResponseEntity<InformationCardDTO> add(@RequestBody AddCardDTO addCardDTO) {
        final InformationCardDTO savedCard = informationCardService.add(addCardDTO);
        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
    }

    @PutMapping("/cards/{cardId}")
    public ResponseEntity<InformationCardDTO> updateCard(@PathVariable UUID cardId, @RequestBody UpdateCardDTO updateCardDTO) {
        final InformationCardDTO updatedCard = informationCardService.update(cardId, updateCardDTO);
        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
    }

    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable UUID cardId) {
        informationCardService.delete(cardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
