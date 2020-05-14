package org.portalizer.demodata.steps;

import org.portalizer.domain.Board;

import java.time.LocalDateTime;

@FunctionalInterface
public interface DateStep {
    LocalDateTime apply();
}
