package by.nestor.credit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public enum TimeUnit {
    DAILY(ChronoUnit.DAYS),
    WEEKLY(ChronoUnit.WEEKS),
    MONTHLY(ChronoUnit.MONTHS);

    public final ChronoUnit unit;

    TimeUnit(ChronoUnit unit) {
        this.unit = unit;
    }

    public LocalDate next(LocalDate date, Integer value) {
        return date.plus(value, unit);
    }

    public long count(LocalDate from, LocalDate to) {
        return to.until(from, ChronoUnit.DAYS);
    }
}
