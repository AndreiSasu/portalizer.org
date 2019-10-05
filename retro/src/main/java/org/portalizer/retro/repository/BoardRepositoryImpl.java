package org.portalizer.retro.repository;

import org.portalizer.retro.domain.Board;

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

public class BoardRepositoryImpl implements BoardRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Board> findFullBoardById(final UUID id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Board> boardCriteriaQuery = criteriaBuilder.createQuery(Board.class);

        Root<Board> boardRoot = boardCriteriaQuery.from(Board.class);
        boardRoot.fetch("columnDefinitions");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(boardRoot.get("id"), id));
        boardCriteriaQuery.select(boardRoot).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Board> typedQuery = entityManager.createQuery(boardCriteriaQuery);
        Board board = typedQuery.getSingleResult();
        return Optional.ofNullable(board);
    }
}
