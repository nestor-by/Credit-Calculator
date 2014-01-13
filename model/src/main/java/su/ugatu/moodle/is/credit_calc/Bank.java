package su.ugatu.moodle.is.credit_calc;

import java.util.Currency;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 19:26
 */
public interface Bank {
    String getName();
    public Currency getDefaultCurrency();
}
