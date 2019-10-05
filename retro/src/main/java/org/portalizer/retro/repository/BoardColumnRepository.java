package org.portalizer.retro.repository;
import org.portalizer.retro.domain.BoardColumn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BoardColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {

}
