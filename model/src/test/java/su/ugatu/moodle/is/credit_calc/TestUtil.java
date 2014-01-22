package su.ugatu.moodle.is.credit_calc;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 20.01.14
 * Time: 21:30
 */
public class TestUtil {

    public static void printProposal(CreditProposal proposal) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<CreditPayment> payments = proposal.getPayments();
        System.out.println("Total: "
           + decimalFormat.format(proposal.getTotalPayment()));
        System.out.println("Initial commission: "
           + decimalFormat.format(proposal.getInitialCreditCommission()));
        System.out.println("Effective rate: "
           + decimalFormat.format(proposal.getEffectiveRate() * 100) + "%");

        for (CreditPayment payment: payments) {
            System.out.print(dateFormat.format(payment.getDate()) + "; ");
            System.out.print(decimalFormat.format(payment.getAmount()) + "; ");
            System.out.print(decimalFormat.format(payment.getDebt()) + "; ");
            System.out.print(decimalFormat.format(payment.getInterest()) +"; ");
            System.out.print(decimalFormat.format(payment.getCommission()) + "; ");
            System.out.print(decimalFormat.format(payment.getTotalLeft())+"; ");
            System.out.println();

        }
    }
}
