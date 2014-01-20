package su.ugatu.moodle.is.credit_calc;

import java.util.Date;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 14.01.14
 * Time: 22:26
 */
public class CreditPaymentImpl implements CreditPayment {

    private final Double amount;
    private final Date date;
    private Double debt;
    private Double totalLeft;
    private Double interest;
    private Double commission;

    public CreditPaymentImpl(final Double amount, final Date date) {
        this.amount = amount;
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public Double getDebt() {
        return debt;
    }

    @Override
    public Double getInterest() {
        return interest;
    }

    @Override
    public Double getTotalLeft() {
        return totalLeft;
    }

    @Override
    public Double getCommission() {
        return commission;
    }

    @Override
    public CreditPayment setDebt(final Double debt) {
        this.debt = debt;
        return this;
    }

    @Override
    public CreditPayment setInterest(final Double interest) {
        this.interest = interest;
        return this;
    }

    @Override
    public CreditPayment setTotalLeft(final Double totalLeft) {
        this.totalLeft = totalLeft;
        return this;
    }

    @Override
    public CreditPayment setCommission(final Double commission) {
        this.commission = commission;
        return this;
    }
}
