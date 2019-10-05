package org.portalizer.retro.repository;

import org.portalizer.retro.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID>, BoardRepositoryCustom {

}
