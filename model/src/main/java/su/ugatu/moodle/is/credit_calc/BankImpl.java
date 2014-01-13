package su.ugatu.moodle.is.credit_calc;

import java.util.Currency;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 19:26
 */
class BankImpl implements Bank {

    private final String name;
    protected Currency defaultCurrency;

    public BankImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }
}
