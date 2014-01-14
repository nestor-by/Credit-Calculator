package su.ugatu.moodle.is.credit_calc;

import java.util.Collection;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 19:28
 */
public interface LendingBank extends Bank {
    Collection<CreditOffer> getCreditOffers();
}
