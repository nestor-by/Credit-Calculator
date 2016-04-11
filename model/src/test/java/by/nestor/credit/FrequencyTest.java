package by.nestor.credit;

import org.junit.Test;

import java.time.LocalDate;

import static by.nestor.credit.TimeUnit.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class FrequencyTest {

    @Test
    public void testNext() throws Exception {
        LocalDate now = LocalDate.parse("2016-01-09");
        LocalDate next = new Frequency(1, DAILY).next(now);
        assertEquals(next.toString(), "2016-01-10");
        next = new Frequency(1, WEEKLY).next(now);
        assertEquals(next.toString(), "2016-01-23");
        next = new Frequency(1, MONTHLY).next(now);
        assertEquals(next.toString(), "2016-02-08");
    }

    @Test
    public void testCount() throws Exception {
        LocalDate to = LocalDate.parse("2016-01-08");
        LocalDate from = LocalDate.parse("2016-04-01");

        System.out.println(new Frequency(1, WEEKLY).days(to));
    }
}