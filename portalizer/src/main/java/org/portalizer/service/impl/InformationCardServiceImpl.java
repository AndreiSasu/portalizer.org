package org.portalizer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.portalizer.domain.Board;
import org.portalizer.domain.InformationCard;
import org.portalizer.repository.BoardRepository;
import org.portalizer.repository.InformationCardRepository;
import org.portalizer.service.InformationCardService;
import org.portalizer.service.dto.AddCardDTO;
import org.portalizer.service.dto.InformationCardDTO;
import org.portalizer.service.dto.ReorderCardDTO;
import org.portalizer.service.dto.UpdateCardDTO;
import org.portalizer.service.mapper.InformationCardMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.UUID;

@Service
@Slf4j
@Validated
public class InformationCardServiceImpl implements InformationCardService {

    private InformationCardMapper informationCardMapper;
    private BoardRepository boardRepository;
    private InformationCardRepository informationCardRepository;

    public InformationCardServiceImpl(InformationCardMapper informationCardMapper, BoardRepository boardRepository, InformationCardRepository informationCardRepository) {
        this.informationCardMapper = informationCardMapper;
        this.boardRepository = boardRepository;
        this.informationCardRepository = informationCardRepository;
    }

    @Override
    public InformationCardDTO add(@Valid AddCardDTO informationCardDTO) {
        final UUID boardId = informationCardDTO.getBoardId();
        validateBoardId(boardId);

        final InformationCard toAdd = new InformationCard();
        toAdd.setText(informationCardDTO.getText());
        toAdd.setColumnKey(informationCardDTO.getColumnKey());
        toAdd.setText(informationCardDTO.getText());

        final Board board = boardRepository.findById(boardId).get();
        toAdd.setBoard(board);

        final InformationCard added = informationCardRepository.save(toAdd);
        return informationCardMapper.toDto(added);
    }

    @Override
    public InformationCardDTO update(final UUID cardId, @Valid UpdateCardDTO updateCardDTO) {
        final UUID boardId = updateCardDTO.getBoardId();
        validateCardId(cardId, boardId);
        final InformationCard informationCard = informationCardRepository.findById(cardId).get();
        informationCard.setColumnKey(updateCardDTO.getColumnKey());
        informationCard.setText(updateCardDTO.getText());
        return informationCardMapper.toDto(informationCardRepository.save(informationCard));
    }

    @Override
    public InformationCardDTO reorder(final UUID cardId, @Valid ReorderCardDTO reorderCardDTO) {
        final InformationCard informationCard = informationCardRepository.findById(cardId).get();
        informationCard.setColumnKey(reorderCardDTO.getNewColumn());
        return  informationCardMapper.toDto(informationCardRepository.save(informationCard));
    }

    @Override
    public void delete(final UUID cardId) {
        informationCardRepository.deleteById(cardId);
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
