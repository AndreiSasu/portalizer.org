package org.portalizer.demodata.steps;

import org.portalizer.domain.Board;

@FunctionalInterface
public interface CardsStep {
    void apply(Board board);
}
