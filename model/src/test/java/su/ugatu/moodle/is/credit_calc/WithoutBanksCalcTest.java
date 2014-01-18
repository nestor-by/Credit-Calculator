package su.ugatu.moodle.is.credit_calc;

import org.junit.Test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author rinat.enikeev@gmail.com
 *         Date: 19.01.14
 *         Time: 2:06
 */
public class WithoutBanksCalcTest {

    @Test
    public void withoutBanksTest() {
        CreditApplication app = new CreditApplicationImpl(100000d);
        app.setDurationInMonths(12);
        app.setPaymentType(CreditPaymentType.ANNUITY);
        CreditProposal proposal = new CreditProposalImpl(app, 0.1699d);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<CreditPayment> payments = proposal.getPayments();
        System.out.println("Total: "
                + decimalFormat.format(proposal.getTotalPayment()));
        System.out.println("Effective rate: "
                + decimalFormat.format(proposal.getEffectiveRate() * 100) + "%");

        for (CreditPayment payment: payments) {
            System.out.print(dateFormat.format(payment.getDate()) + "; ");
            System.out.print(decimalFormat.format(payment.getAmount()) + "; ");
            System.out.print(decimalFormat.format(payment.getDebt()) + "; ");
            System.out.print(decimalFormat.format(payment.getInterest()) +"; ");
            System.out.print(decimalFormat.format(payment.getTotalLeft())+"; ");
            System.out.println();

        }
    }
}
