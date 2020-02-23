package org.portalizer.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import org.portalizer.service.dto.ReorderColumnsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/retro/columns")
public class ColumnResource {

    @PutMapping("/reorder")
    public ResponseEntity<Void> reorderColumns(@RequestParam(required = true) final UUID boardId, @RequestParam(required = true) final ReorderColumnsDTO reorderColumnsRequest) {
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert("",
            false,
            "board", boardId.toString())).build();
    }

}
