package su.ugatu.moodle.is.credit_calc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
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
    private Double minAmount;
    @XmlElement
    private Double maxAmount;
    @XmlElement
    private Double rate;
    @XmlElement
    private String currencyName;
    @XmlElement
    private Integer minMonthDuration;
    @XmlElement
    private Integer maxMonthDuration;
    @XmlElement
    private Double onceCommissionAmount;
    @XmlElement
    private Double onceCommissionPercent;
    @XmlElement
    private Double monthlyCommissionAmount;
    @XmlElement
    private Double monthlyCommissionPercent;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getMinAmount() {
        return minAmount;
    }

    @Override
    public Double getMaxAmount() {
        return maxAmount;
    }

    @Override
    public Double getRate() {
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
    public Double getMonthlyCommissionPercent() {
        return monthlyCommissionPercent;
    }

    @Override
    public Double getOnceCommissionAmount() {
        return onceCommissionAmount;
    }

    @Override
    public Double getOnceCommissionPercent() {
        return onceCommissionPercent;
    }

    @Override
    public Double getMonthlyCommissionAmount() {
        return monthlyCommissionAmount;
    }

    @Override
    public CreditProposal calculateProposal(final CreditApplication application) {
        return calculateProposal(null, application);
    }

    @Override
    public CreditProposal calculateProposal(final Customer customer,
                                            final CreditApplication creditApplication) {
        if(!applicationCorrespondsToOffer(creditApplication)) return null;
        configureApplicationBlankParams(creditApplication);
        return new CreditProposalImpl(creditApplication, this);
    }

    @Override
    public CreditOffer setRate(final Double rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public CreditOffer setOnceCommissionAmount(final Double onceCommissionAmount) {
        this.onceCommissionAmount = onceCommissionAmount;
        return this;
    }

    @Override
    public CreditOffer setOnceCommissionPercent(final Double onceCommissionPercent) {
        this.onceCommissionPercent = onceCommissionPercent;
        return this;
    }

    @Override
    public CreditOffer setMonthlyCommissionAmount(final Double monthlyCommissionAmount) {
        this.monthlyCommissionAmount = monthlyCommissionAmount;
        return this;
    }

    @Override
    public CreditOffer setMonthlyCommissionPercent(final Double monthlyCommissionPercent) {
        this.monthlyCommissionPercent = monthlyCommissionPercent;
        return this;
    }

    /**
     * Sets payment type, currency and duration of application to offers values
     * if some of them or all are null.
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
     * @param creditApplication incoming application
     * @return true if application is correct in context of this offer.
     */
    private boolean applicationCorrespondsToOffer(final CreditApplication creditApplication) {

        Double amount = creditApplication.getAmount();
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

    private boolean amountInBounds(final Double amount) {
        boolean amountInBounds = true;
        if (getMaxAmount() != null) {
            amountInBounds = amount <= getMaxAmount();
        }
        if (getMinAmount() != null) {
            amountInBounds = amountInBounds && amount >= getMinAmount();
        }
        return amountInBounds;
    }
}
