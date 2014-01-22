package su.ugatu.moodle.is.credit_calc;


import java.math.BigDecimal;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 21:47
 */
public interface CreditOffer {
    String getName();

    BigDecimal getMinAmount();

    BigDecimal getMaxAmount();

    BigDecimal getRate();

    String getCurrency();

    Integer getMinMonthDuration();

    Integer getMaxMonthDuration();

    BigDecimal getMonthlyCommissionPercent();

    BigDecimal getOnceCommissionAmount();

    BigDecimal getOnceCommissionPercent();

    BigDecimal getMonthlyCommissionAmount();

    CreditProposal calculateProposal(CreditApplication application);

    /**
     * Calculates {@link CreditProposal} on a given {@link Customer} and
     * {@link CreditApplication}.
     *
     * @param customer          incoming customer.
     * @param creditApplication incoming application.
     * @return {@link CreditProposal} of {@code null} if application does not
     *         correspond to offer.
     */
    CreditProposal calculateProposal(Customer customer,
                                     CreditApplication creditApplication);

    CreditOffer setRate(BigDecimal rate);

    CreditOffer setOnceCommissionAmount(BigDecimal onceCommissionAmount);

    CreditOffer setOnceCommissionPercent(BigDecimal onceCommissionPercent);

    CreditOffer setMonthlyCommissionAmount(BigDecimal monthlyCommissionAmount);

    CreditOffer setMonthlyCommissionPercent(BigDecimal monthlyCommissionPercent);
}
