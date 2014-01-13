package su.ugatu.moodle.is.credit_calc;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.Currency;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:06
 */
public class CreditExamplesTest extends Assert {

    private final Logger LOG = Logger.getLogger(CreditExamplesTest.class);

    @Test
    public void getCreditAmountOnly() {
        Double creditAmount = 100d;

        LendingBank bank = BankFactory.getInstance()
                                .getBanks().iterator().next();

        Customer customer = new CustomerImpl().setSex(Sex.MALE);

        CreditApplication creditApplication
                = new CreditApplicationImpl(creditAmount);

//        todo: CreditProposal creditProposal
//                = bank.getCreditProposal(customer, creditApplication);

//        if (creditProposal == null) { // bank refused credit for customer
//            LOG.info(bank.getDeclineReason(creditProposal));
//        }
//
//        if (!customer.checkCreditProposal(creditProposal)) {
//            fail("customer refused credit proposal");
//        }
//
//        LOG.info("Customer accepts credit offer: \n" + creditProposal);
//        bank.addCreditRecord(creditProposal);
//        customer.addCreditRecord(creditProposal);
    }
}
