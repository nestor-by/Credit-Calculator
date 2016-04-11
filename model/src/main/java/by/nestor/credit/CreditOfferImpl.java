package by.nestor.credit;

import by.nestor.credit.commission.Commission;
import by.nestor.credit.payments.CreditPaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Реализация кредитного оффера. ы
 *
 * @author rinat.enikeev@gmail.com
 *         Date: 13.01.14
 *         Time: 21:47
 */
public class CreditOfferImpl implements CreditOffer {

    private static final CreditPaymentType DEFAULT_PAYMENT_TYPE = CreditPaymentType.ANNUITY;

    private String name;
    private BigDecimal rate;
    private String currencyName;
    private Interval<BigDecimal> amount;
    private Interval<Integer> duration;
    private Frequency frequency;
    private Collection<Commission> commissions = Collections.emptyList();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Interval<BigDecimal> getAmount() {
        return amount;
    }


    @Override
    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String getCurrency() {
        return currencyName;
    }

    @Override
    public Interval<Integer> getDuration() {
        return duration;
    }

    @Override
    public Frequency getFrequency() {
        return frequency;
    }

    @Override
    public Collection<Commission> getCommissions() {
        return commissions;
    }


    @Override
    public CreditProposal calculateProposal(final CreditApplication application) {
        if (!applicationCorrespondsToOffer(application)) return null;
        configureApplicationBlankParams(application);
        return new CreditProposalBuilder(this).build(application);
    }

    @Override
    public CreditOffer setRate(final BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public CreditOffer setCommissions(List<Commission> commissions) {
        this.commissions = commissions;
        return this;
    }

    @Override
    public CreditOffer setCommissions(Commission... commissions) {
        return setCommissions(Arrays.asList(commissions));
    }

    @Override
    public CreditOffer setFrequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }


    /**
     * Sets payment type, currency and duration of application to offers values
     * if some of them or all are null.
     *
     * @param creditApplication incoming application
     */
    private void configureApplicationBlankParams(final CreditApplication creditApplication) {
        if (creditApplication.getCurrency() == null) {
            creditApplication.setCurrency(getCurrency());
        }
        if (creditApplication.getDurations() == null) {
            creditApplication.setDurations(new Duration(this.duration.max, frequency, DEFAULT_PAYMENT_TYPE));
        }
        if (creditApplication.getStartDate() == null) {
            creditApplication.setStartDate(LocalDate.now());
        }
    }

    /**
     * Checks if applications parameters are in bounds of current offer.
     *
     * @param creditApplication incoming application
     * @return true if application is correct in context of this offer.
     */
    private boolean applicationCorrespondsToOffer(final CreditApplication creditApplication) {

        BigDecimal amount = creditApplication.getAmount();
        if (amount != null && this.amount != null && !this.amount.inBounds(amount))
            return false;
        List<Duration> durations = creditApplication.getDurations();
        for (Duration duration : durations) {
            if (duration != null && this.duration != null && !this.duration.inBounds(duration.value))
                return false;
        }

        String currency = creditApplication.getCurrency();
        return !(currency != null
                && getCurrency() != null
                && !currency.equals(getCurrency()));

    }

}
