package org.portalizer.demodata.steps;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

@Component
public class FakerDateStep implements DateStep {

    private final Faker faker;

    private final Date startDate = Date.
        from(LocalDateTime
        .of(2015, Month.JANUARY, 1, 0, 0)
        .atZone(ZoneId.systemDefault()).toInstant());

    private final Date now = Date
        .from(LocalDateTime.now()
            .atZone(ZoneId.systemDefault()).toInstant());

    public FakerDateStep(Faker faker) {
        this.faker = faker;
    }

    @Override
    public LocalDateTime apply() {
        final Date fakeDate = faker.date().between(startDate, now);
        return Instant.ofEpochMilli(fakeDate.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

}
