package su.ugatu.moodle.is.credit_calc;

import su.ugatu.moodle.is.credit_calc.customer.Customer;
import su.ugatu.moodle.is.util.BigDecimalAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Реализация кредитного оффера. ы
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 21:47
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "creditOffer")
public class CreditOfferImpl implements CreditOffer {

    private static final CreditPaymentType DEFAULT_PAYMENT_TYPE = CreditPaymentType.ANNUITY;

    @XmlElement
    private String name;
    @XmlElement
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal minAmount;
    @XmlElement
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal maxAmount;
    @XmlElement
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal rate;
    @XmlElement
    private String currencyName;
    @XmlElement
    private Integer minMonthDuration;
    @XmlElement
    private Integer maxMonthDuration;
    @XmlElement
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal onceCommissionAmount;
    @XmlElement
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal onceCommissionPercent;
    @XmlElement
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal monthlyCommissionAmount;
    @XmlElement
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal monthlyCommissionPercent;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getMinAmount() {
        return minAmount;
    }

    @Override
    public BigDecimal getMaxAmount() {
        return maxAmount;
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
    public Integer getMinMonthDuration() {
        return minMonthDuration;
    }

    @Override
    public Integer getMaxMonthDuration() {
        return maxMonthDuration;
    }

    @Override
    public BigDecimal getMonthlyCommissionPercent() {
        return monthlyCommissionPercent;
    }

    @Override
    public BigDecimal getOnceCommissionAmount() {
        return onceCommissionAmount;
    }

    @Override
    public BigDecimal getOnceCommissionPercent() {
        return onceCommissionPercent;
    }

    @Override
    public BigDecimal getMonthlyCommissionAmount() {
        return monthlyCommissionAmount;
    }

    @Override
    public CreditProposal calculateProposal(final CreditApplication application) {
        return calculateProposal(null, application);
    }

    @Override
    public CreditProposal calculateProposal(final Customer customer,
                                            final CreditApplication creditApplication) {
        if (!applicationCorrespondsToOffer(creditApplication)) return null;
        configureApplicationBlankParams(creditApplication);
        return new CreditProposalImpl(creditApplication, this);
    }

    @Override
    public CreditOffer setRate(final BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public CreditOffer setOnceCommissionAmount(final BigDecimal onceCommissionAmount) {
        this.onceCommissionAmount = onceCommissionAmount;
        return this;
    }

    @Override
    public CreditOffer setOnceCommissionPercent(final BigDecimal onceCommissionPercent) {
        this.onceCommissionPercent = onceCommissionPercent;
        return this;
    }

    @Override
    public CreditOffer setMonthlyCommissionAmount(final BigDecimal monthlyCommissionAmount) {
        this.monthlyCommissionAmount = monthlyCommissionAmount;
        return this;
    }

    @Override
    public CreditOffer setMonthlyCommissionPercent(final BigDecimal monthlyCommissPercent) {
        this.monthlyCommissionPercent = monthlyCommissPercent;
        return this;
    }

    /**
     * Sets payment type, currency and duration of application to offers values
     * if some of them or all are null.
     *
     * @param creditApplication incoming application
     */
    private void configureApplicationBlankParams(final CreditApplication creditApplication) {
        if (creditApplication.getPaymentType() == null) {
            creditApplication.setPaymentType(DEFAULT_PAYMENT_TYPE);
        }
        if (creditApplication.getCurrency() == null) {
            creditApplication.setCurrency(getCurrency());
        }
        if (creditApplication.getDurationInMonths() == null) {
            creditApplication.setDurationInMonths(getMaxMonthDuration());
        }
        if (creditApplication.getStartDate() == null) {
            creditApplication.setStartDate(new Date());
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
        if (!amountInBounds(amount)) return false;

        Integer duration = creditApplication.getDurationInMonths();
        if (duration != null && !durationInBounds(duration)) return false;

        String currency = creditApplication.getCurrency();
        if (currency != null && getCurrency() != null
                && !currency.equals(getCurrency())) {
            return false;
        }

        return true;
    }

    private boolean durationInBounds(final Integer duration) {
        boolean durInBounds = true;
        if (getMaxMonthDuration() != null) {
            durInBounds = duration <= getMaxMonthDuration();
        }
        if (getMinMonthDuration() != null) {
            durInBounds = durInBounds && duration >= getMinMonthDuration();
        }
        return durInBounds;
    }

    private boolean amountInBounds(final BigDecimal amount) {
        boolean amountInBounds = true;
        if (getMaxAmount() != null) {
            amountInBounds = amount.compareTo(getMaxAmount()) <= 0;
        }
        if (getMinAmount() != null) {
            amountInBounds = amountInBounds && amount.compareTo(getMinAmount()) >= 0;
        }
        return amountInBounds;
    }
}
