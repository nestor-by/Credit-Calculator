package su.ugatu.moodle.is.credit_calc;

import org.junit.Test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author rinat.enikeev@gmail.com
 *         Date: 19.01.14
 *         Time: 1:18
 */
public class DifferentialCreditTest {

    @Test
    public void calcDiffCreditTest() {
        Double creditAmount = 100000d;

        LendingBank bank
                = BankFactory.getInstance().getLendingBank("Альфа-Банк");

        Customer customer = new CustomerImpl();

        CreditApplication creditApplication
                = new CreditApplicationImpl(creditAmount).setDurationInMonths(12);
        creditApplication.setPaymentType(CreditPaymentType.DIFFERENTIAL);

        CreditProposal creditProposal = bank.getCreditProposals(
                customer, creditApplication).iterator().next();

        TestUtil.printProposal(creditProposal);
    }
}
