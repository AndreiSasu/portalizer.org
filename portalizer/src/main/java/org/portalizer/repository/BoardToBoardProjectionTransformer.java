package org.portalizer.repository;

import org.hibernate.transform.ResultTransformer;
import org.portalizer.service.dto.BoardProjectionDTO;

import java.util.List;
import java.util.UUID;

public class BoardToBoardProjectionTransformer implements ResultTransformer {

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        return new BoardProjectionDTO((UUID) tuple[0], (String) tuple[1], (String) tuple[2]);
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }
}
