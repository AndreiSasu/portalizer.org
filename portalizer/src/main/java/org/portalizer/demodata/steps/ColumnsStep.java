package org.portalizer.demodata.steps;

import org.portalizer.domain.Board;

@FunctionalInterface
public interface ColumnsStep {
    void apply(Board board);
}
