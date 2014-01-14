package su.ugatu.moodle.is.credit_calc;

import java.util.Currency;

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
    Currency getCurrency();
    Integer getMinMonthDuration();
    Integer getMaxMonthDuration();

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
}
