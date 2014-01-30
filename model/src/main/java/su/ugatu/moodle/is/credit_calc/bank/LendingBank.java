package su.ugatu.moodle.is.credit_calc.bank;

import su.ugatu.moodle.is.credit_calc.CreditApplication;
import su.ugatu.moodle.is.credit_calc.CreditOffer;
import su.ugatu.moodle.is.credit_calc.CreditProposal;
import su.ugatu.moodle.is.credit_calc.customer.Customer;

import java.util.Collection;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 19:28
 */
public interface LendingBank extends Bank {
    Collection<CreditOffer> getCreditOffers();

    Collection<CreditProposal> getCreditProposals(Customer customer,
                                                  CreditApplication creditApplication);
}
