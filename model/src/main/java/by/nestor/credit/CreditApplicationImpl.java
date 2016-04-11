package by.nestor.credit;

import by.nestor.credit.payments.CreditPaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Реализация заявки на кредит.
 *
 * @author rinat.enikeev@gmail.com
 *         Date: 13.01.14
 *         Time: 18:50
 */
public class CreditApplicationImpl implements CreditApplication {

    private final BigDecimal amount;        // размер кредита
    private String currency;                // валюта кредита (ISO 4217)
    private CreditPaymentType paymentType;  // тип платежа (аннуитет, дифф.)
    private List<Duration> durations;       // срок кредита в месяцах
    private LocalDate startDate;            // дата получения кредита

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
    public List<Duration> getDurations() {
        return durations;
    }

    @Override
    public CreditApplication setDurations(List<Duration> durations) {
        this.durations = durations;
        return this;
    }

    @Override
    public CreditApplication setDurations(Duration... durations) {
        return setDurations(Arrays.asList(durations));
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
