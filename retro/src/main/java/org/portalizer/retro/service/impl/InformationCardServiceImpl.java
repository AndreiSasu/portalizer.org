package org.portalizer.retro.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.portalizer.retro.repository.BoardRepository;
import org.portalizer.retro.repository.InformationCardRepository;
import org.portalizer.retro.service.InformationCardService;
import org.portalizer.retro.service.dto.InformationCardDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.UUID;

@Service
@Slf4j
@Validated
@AllArgsConstructor
public class InformationCardServiceImpl implements InformationCardService {

    private BoardRepository boardRepository;
    private InformationCardRepository informationCardRepository;

    @Override
    public InformationCardDTO add(@Valid InformationCardDTO informationCardDTO) {
        final UUID boardId = informationCardDTO.getBoardId();
        validateBoardId(boardId);
        return null;
    }

    @Override
    public InformationCardDTO update(@Valid InformationCardDTO informationCardDTO) {
        final UUID boardId = informationCardDTO.getBoardId();
        final UUID cardId = informationCardDTO.getCardId();
        validateCardId(cardId, boardId);
        return null;
    }

    @Override
    public void delete(UUID cardId) {

    }

    @Override
    public InformationCardDTO merge(@Valid InformationCardDTO informationCardDTO) {
        return null;
    }


    private InformationCardService validateBoardId(final UUID boardId) {
        if(!boardRepository.existsById(boardId)) {
            throw new ValidationException(String.format("Board with id: %s does not exist!", boardId));
        }
        return this;
    }

    private InformationCardService validateCardId(final UUID cardId, final UUID boardId) {
        if(!informationCardRepository.existsByIdAndBoardId(cardId, boardId)) {
            throw new ValidationException(String.format("Board with board id: %s and card id: %s does not exist!", boardId, cardId));
        }
        return this;
    }
}
