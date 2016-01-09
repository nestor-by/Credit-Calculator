package by.nestor.credit;

import by.nestor.credit.payments.CreditPayment;

import java.util.List;

import static by.nestor.credit.Constants.*;

/**
 * @author rinat.enikeev@gmail.com
 *         Date: 20.01.14
 *         Time: 21:30
 */
public class TestUtil {

    public static void printProposal(CreditProposal proposal) {

        List<CreditPayment> payments = proposal.getPayments();
        System.out.println("Total: " + proposal.getTotalPayment());
        System.out.println("Initial commission: " + proposal.getInitialCommission());
        System.out.println("Effective rate: " + proposal.getEffectiveRate());

        for (CreditPayment payment : payments) {
            System.out.print(payment.getDate() + "; ");
            System.out.print(round(payment.getAmount(), OUTPUT_AMOUNT_SCALE) + ";\t ");
            System.out.print(round(payment.getDebt(), OUTPUT_AMOUNT_SCALE) + ";\t ");
            System.out.print(round(payment.getInterest(), OUTPUT_PERCENT_SCALE) + ";\t ");
            System.out.print(round(payment.getCommission(), OUTPUT_PERCENT_SCALE) + ";\t ");
            System.out.print(round(payment.getTotalLeft(), OUTPUT_AMOUNT_SCALE) + "; ");
            System.out.println();
        }
    }

    public static void printApplication(final CreditApplication application) {
        System.out.println("Application: ");
        System.out.println("\tAmount: " + round(application.getAmount(), OUTPUT_AMOUNT_SCALE));
        System.out.println("\t" + application.getDuration());

    }
}
