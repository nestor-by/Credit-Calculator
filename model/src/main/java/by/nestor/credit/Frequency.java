package by.nestor.credit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class Frequency {
    private final Integer value;
    private final TimeUnit unit;

    public Frequency(Integer value, TimeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public Integer getValue() {
        return value;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public LocalDate next(LocalDate date) {
        return unit.next(date, value);
    }

    public long days(LocalDate date) {
        LocalDate next = unit.next(date, value);
        return unit.count(next, date);
    }

    public long days() {
        return unit.unit.getDuration().getSeconds() * value / ChronoUnit.DAYS.getDuration().getSeconds();
    }

}
