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
class CreditOfferImpl implements CreditOffer {

    private static final CreditPaymentType DEFAULT_PAYMENT_TYPE = CreditPaymentType.ANNUITY;

    @XmlElement
    private String name;
    @XmlElement
    private Double minAmount;
    @XmlElement
    private Double maxAmount;
    @XmlElement
    private Double minRate;
    @XmlElement
    private String currencyName;
    @XmlElement
    private Integer minMonthDuration;
    @XmlElement
    private Integer maxMonthDuration;

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
        return minRate;
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
    public CreditProposal calculateProposal(final Customer customer,
                                            final CreditApplication creditApplication) {
        if(!applicationCorrespondsToOffer(creditApplication)) return null;
        configureApplicationBlankParams(creditApplication);
        return new CreditProposalImpl(creditApplication, this);
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
        boolean amountInBoundsOfOffer = amount <= getMaxAmount()
                                        && amount >= getMinAmount();
        if (!amountInBoundsOfOffer) {
            return false;
        }

        Integer duration = creditApplication.getDurationInMonths();
        if (duration != null) {
            boolean durInBounds = duration <= getMaxMonthDuration();
            durInBounds = durInBounds && duration >= getMinMonthDuration();
            if (!durInBounds) return false;
        }

        if (creditApplication.getCurrency() != null
                && !creditApplication.getCurrency().equals(getCurrency())) {
            return false;
        }
        return true;
    }
}
