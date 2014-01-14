package su.ugatu.moodle.is.credit_calc;

import java.util.Currency;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
public class CreditApplicationImpl implements CreditApplication {

    private final Double amount;
    private Currency currency;
    private CreditPaymentType paymentType;
    private Integer durationInMonths;

    public CreditApplicationImpl(final Double amount) {
        this.amount = amount;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public CreditPaymentType getPaymentType() {
        return paymentType;
    }

    @Override
    public Integer getDurationInMonths() {
        return durationInMonths;
    }

    @Override
    public CreditApplication setCurrency(final Currency currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public CreditApplication setPaymentType(final CreditPaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    @Override
    public CreditApplication setDurationInMonths(final Integer durationInMonths) {
        this.durationInMonths = durationInMonths;
        return this;
    }

}
