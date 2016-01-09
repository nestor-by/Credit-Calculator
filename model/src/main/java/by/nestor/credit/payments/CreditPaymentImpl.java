package by.nestor.credit.payments;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Реализация платежа по кредиту.
 *
 * @author rinat.enikeev@gmail.com
 *         Date: 14.01.14
 *         Time: 22:26
 */
public class CreditPaymentImpl implements CreditPayment {

    private final BigDecimal amount; // суммарный размер платежа
    private final LocalDate date;         // дата платежа по графику
    private BigDecimal debt;         // долговая часть
    private BigDecimal totalLeft;    // всего осталось по кредиту
    private BigDecimal interest;     // процентная часть платежа
    private BigDecimal commission;   // комиссия

    public CreditPaymentImpl(final BigDecimal amount, final LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    @Override
    public LocalDate getDate() {
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
