package org.portalizer.web.rest;

import org.portalizer.service.dto.CounterDTO;
import org.portalizer.service.insights.CounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/retro/insights")
public class InsightsResource {

    private CounterService counterService;

    public InsightsResource(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/counters")
    public ResponseEntity<List<CounterDTO>> overAllCounters() {
        return new ResponseEntity<>(counterService.getOverAllCounters(), HttpStatus.OK);
    }

}
