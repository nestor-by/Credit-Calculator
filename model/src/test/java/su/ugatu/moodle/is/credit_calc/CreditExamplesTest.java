package su.ugatu.moodle.is.credit_calc;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import su.ugatu.moodle.is.credit_calc.bank.BankFactory;
import su.ugatu.moodle.is.credit_calc.bank.LendingBank;
import su.ugatu.moodle.is.credit_calc.customer.Customer;
import su.ugatu.moodle.is.credit_calc.customer.CustomerImpl;

import java.math.BigDecimal;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:06
 */
public class CreditExamplesTest extends Assert {

    private final Logger LOG = Logger.getLogger(CreditExamplesTest.class);

    @Test
    public void getCreditAmountOnly() {
        BigDecimal creditAmount = new BigDecimal(100000);

        LendingBank bank
                = BankFactory.getInstance().getLendingBank("Альфа-Банк");

        Customer customer = new CustomerImpl();

        CreditApplication creditApplication
              = new CreditApplicationImpl(creditAmount).setDurationInMonths(12);

        CreditProposal creditProposal = bank.getCreditProposals(
                                customer, creditApplication).iterator().next();

        TestUtil.printProposal(creditProposal);
    }
}
