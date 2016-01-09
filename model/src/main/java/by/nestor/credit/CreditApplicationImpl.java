package by.nestor.credit;

import by.nestor.credit.payments.CreditPaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Реализация заявки на кредит.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
public class CreditApplicationImpl implements CreditApplication {

    private final BigDecimal amount;       // размер кредита
    private String currency;               // валюта кредита (ISO 4217)
    private CreditPaymentType paymentType; // тип платежа (аннуитет, дифф.)
    private Integer durationInMonths;      // срок кредита в месяцах
    private LocalDate startDate;                // дата получения кредита

    public CreditApplicationImpl(final BigDecimal anAmount) {
        this.amount = anAmount;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public CreditApplication setCurrency(final String aCurrency) {
        this.currency = aCurrency;
        return this;
    }

    @Override
    public CreditPaymentType getPaymentType() {
        return paymentType;
    }

    @Override
    public CreditApplication setPaymentType(CreditPaymentType aPaymentType) {
        this.paymentType = aPaymentType;
        return this;
    }

    @Override
    public Integer getDuration() {
        return durationInMonths;
    }

    @Override
    public CreditApplication setDuration(Integer aDurationInMonths) {
        this.durationInMonths = aDurationInMonths;
        return this;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    public CreditApplication setStartDate(final LocalDate aStartDate) {
        this.startDate = aStartDate;
        return this;
    }

}
