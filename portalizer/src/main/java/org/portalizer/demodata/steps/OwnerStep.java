package org.portalizer.demodata.steps;

import org.portalizer.domain.Board;

@FunctionalInterface
public interface OwnerStep {
    void apply(Board board);
}
