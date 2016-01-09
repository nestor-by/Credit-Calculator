package by.nestor.credit;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class IntervalTest {

    @Test
    public void testInBounds() throws Exception {
        Interval<Integer> interval = new Interval<>(0, 10);
        assertTrue(interval.inBounds(0));
        assertTrue(interval.inBounds(5));
        assertTrue(interval.inBounds(10));
        assertFalse(interval.inBounds(11));
        assertFalse(interval.inBounds(-1));
        interval = new Interval<>(null, 10);
        assertTrue(interval.inBounds(-1));
        assertFalse(interval.inBounds(11));
        interval = new Interval<>(0, null);
        assertFalse(interval.inBounds(-1));
        assertTrue(interval.inBounds(11));
    }
}