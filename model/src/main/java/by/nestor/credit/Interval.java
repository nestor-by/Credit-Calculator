package by.nestor.credit;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class Interval<T extends Comparable<T>> {
    public final T min;
    public final T max;

    public Interval(T min, T max) {
        this.min = min;
        this.max = max;
    }


    public boolean inBounds(final T value) {
        if (value == null)
            return false;
        boolean amountInBounds = true;
        if (max != null) {
            amountInBounds = value.compareTo(max) <= 0;
        }
        if (min != null) {
            amountInBounds = amountInBounds && value.compareTo(min) >= 0;
        }
        return amountInBounds;
    }

}
