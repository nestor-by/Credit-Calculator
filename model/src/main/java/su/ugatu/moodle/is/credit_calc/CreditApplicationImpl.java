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

    public CreditApplication setCurrency(final Currency currency) {
        this.currency = currency;
        return this;
    }

    public CreditApplication setPaymentType(final CreditPaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

}
