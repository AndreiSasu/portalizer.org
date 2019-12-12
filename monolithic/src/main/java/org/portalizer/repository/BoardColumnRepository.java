package org.portalizer.repository;

import org.portalizer.domain.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BoardColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {

}
