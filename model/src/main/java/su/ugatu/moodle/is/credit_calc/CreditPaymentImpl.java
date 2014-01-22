package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 14.01.14
 * Time: 22:26
 */
public class CreditPaymentImpl implements CreditPayment {

    private final BigDecimal amount;
    private final Date date;
    private BigDecimal debt;
    private BigDecimal totalLeft;
    private BigDecimal interest;
    private BigDecimal commission;

    public CreditPaymentImpl(final BigDecimal amount, final Date date) {
        this.amount = amount;
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public BigDecimal getDebt() {
        return debt;
    }

    @Override
    public BigDecimal getInterest() {
        return interest;
    }

    @Override
    public BigDecimal getTotalLeft() {
        return totalLeft;
    }

    @Override
    public BigDecimal getCommission() {
        return commission;
    }

    @Override
    public CreditPayment setDebt(final BigDecimal debt) {
        this.debt = debt;
        return this;
    }

    @Override
    public CreditPayment setInterest(final BigDecimal interest) {
        this.interest = interest;
        return this;
    }

    @Override
    public CreditPayment setTotalLeft(final BigDecimal totalLeft) {
        this.totalLeft = totalLeft;
        return this;
    }

    @Override
    public CreditPayment setCommission(final BigDecimal commission) {
        this.commission = commission;
        return this;
    }
}
