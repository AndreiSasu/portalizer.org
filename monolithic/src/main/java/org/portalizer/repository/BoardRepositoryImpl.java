package org.portalizer.repository;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.portalizer.domain.Board;
import org.portalizer.domain.InformationCard;
import org.portalizer.service.dto.BoardProjectionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
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

    @Autowired
    EntityManagerFactory sessionFactory;

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
    public List<BoardProjectionDTO> searchFuzzy(final String fieldName, final String searchText) {
        Query fuzzyQuery = getQueryBuilder()
            .keyword()
            .fuzzy()
            .withEditDistanceUpTo(2)
            .withPrefixLength(1)
            .onField(fieldName)
            .matching(searchText)
            .createQuery();

        List<BoardProjectionDTO> results = getJpaQuery(fuzzyQuery, true).getResultList();

        return results;
    }

    @Override
    public List<BoardProjectionDTO> searchWildCard(final String fieldName, final String searchText) {
        Query wildcardQuery = getQueryBuilder()
            .keyword()
            .wildcard()
            .onField(fieldName)
            .matching(searchText+"*")
            .createQuery();

        List<BoardProjectionDTO> results = getJpaQuery(wildcardQuery, true).getResultList();

        return results;
    }

    @Override
    public List<BoardProjectionDTO> searchPhrase(final String fieldName, final String searchText) {
        Query phraseQuery = getQueryBuilder()
            .phrase()
            .withSlop(1)
            .onField(fieldName)
            .sentence(searchText)
            .createQuery();

        List<BoardProjectionDTO> results = getJpaQuery(phraseQuery, true).getResultList();

        return results;
    }

    private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery, boolean projection) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        final FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Board.class);
        if(projection) {
            fullTextQuery.setProjection("id", "name", "description")
                .setResultTransformer(new BoardToBoardProjectionTransformer());
        }
        return fullTextQuery;
    }

    private QueryBuilder getQueryBuilder() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
            .buildQueryBuilder()
            .forEntity(Board.class)
            .get();
    }
}
