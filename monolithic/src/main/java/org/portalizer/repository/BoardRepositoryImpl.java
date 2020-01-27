package org.portalizer.repository;

import org.portalizer.domain.Board;
import org.portalizer.domain.InformationCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        board.setInformationCards(informationCards.orElse(new ArrayList<>()));
        return Optional.ofNullable(board);
    }

    @Override
    public List<Board> searchFuzzy(final String fieldName, final String searchText) {
        return null;
    }

    @Override
    public List<Board> searchWildCard(final String fieldName, final String searchText) {
        return null;
    }

    @Override
    public List<Board> searchPhrase(final String fieldName, final String searchText) {
        return null;
    }
}
