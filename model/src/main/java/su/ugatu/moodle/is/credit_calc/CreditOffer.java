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
    Double getMinRate();
    Currency getCurrency();
}
