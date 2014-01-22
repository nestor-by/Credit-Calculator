package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
public class CreditApplicationImpl implements CreditApplication {

    private final BigDecimal amount;
    private String currency;
    private CreditPaymentType paymentType;
    private Integer durationInMonths;
    private Date startDate;

    public CreditApplicationImpl(final BigDecimal amount) {
        this.amount = amount;
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
    public CreditApplication setCurrency(final String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public CreditPaymentType getPaymentType() {
        return paymentType;
    }

    @Override
    public CreditApplication setPaymentType(final CreditPaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    @Override
    public Integer getDurationInMonths() {
        return durationInMonths;
    }

    @Override
    public CreditApplication setDurationInMonths(final Integer durationInMonths) {
        this.durationInMonths = durationInMonths;
        return this;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public CreditApplication setStartDate(final Date startDate) {
        this.startDate = startDate;
        return this;
    }

}
