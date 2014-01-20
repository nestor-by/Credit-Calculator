package su.ugatu.moodle.is.credit_calc;


/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 21:47
 */
public interface CreditOffer {
    String getName() ;
    Double getMinAmount();
    Double getMaxAmount();
    Double getRate();
    String getCurrency();
    Integer getMinMonthDuration();
    Integer getMaxMonthDuration();
    Double getMonthlyCommissionPercent();
    Double getOnceCommissionAmount();
    Double getOnceCommissionPercent();
    Double getMonthlyCommissionAmount();

    CreditProposal calculateProposal(CreditApplication application);
    /**
     * Calculates {@link CreditProposal} on a given {@link Customer} and
     * {@link CreditApplication}.
     *
     * @param customer incoming customer.
     * @param creditApplication incoming application.
     * @return {@link CreditProposal} of {@code null} if application does not
     * correspond to offer.
     */
    CreditProposal calculateProposal(Customer customer,
                                     CreditApplication creditApplication);

    CreditOffer setRate(Double rate);

    CreditOffer setOnceCommissionAmount(Double onceCommissionAmount);

    CreditOffer setOnceCommissionPercent(Double onceCommissionPercent);

    CreditOffer setMonthlyCommissionAmount(Double monthlyCommissionAmount);

    CreditOffer setMonthlyCommissionPercent(Double monthlyCommissionPercent);
}
