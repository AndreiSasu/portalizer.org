package org.portalizer.retro.repository;

import org.portalizer.retro.domain.Board;
import org.portalizer.retro.domain.InformationCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    @Autowired
    InformationCardRepository informationCardRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Board> findFullBoardById(final UUID id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Board> boardCriteriaQuery = criteriaBuilder.createQuery(Board.class);

        Root<Board> boardRoot = boardCriteriaQuery.from(Board.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(boardRoot.get("id"), id));
        boardCriteriaQuery.select(boardRoot).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Board> typedQuery = entityManager.createQuery(boardCriteriaQuery);
        Board board = typedQuery.getSingleResult();

        //get all information cards
        Optional<List<InformationCard>> informationCards = informationCardRepository.findAllByBoardId(board.getId());
        if(informationCards.isPresent()) {
            board.setInformationCards(informationCards.get());
        }
        return Optional.ofNullable(board);
    }
}
