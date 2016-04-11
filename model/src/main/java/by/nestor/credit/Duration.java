package by.nestor.credit;

import by.nestor.credit.payments.CreditPaymentType;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class Duration {
    public final Integer value;
    public final Frequency frequency;
    public final CreditPaymentType paymentType;

    public Duration(Integer value, Frequency frequency, CreditPaymentType paymentType) {
        this.value = value;
        this.frequency = frequency;
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return super.toString() + " " + value + " " + frequency;
    }

    public long days() {
        return value * frequency.days();
    }
}
