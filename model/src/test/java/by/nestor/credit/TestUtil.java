package by.nestor.credit;

import by.nestor.credit.payments.CreditPayment;

import java.util.List;

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
            System.out.print(payment.getAmount() + "; ");
            System.out.print(payment.getDebt() + "; ");
            System.out.print(payment.getInterest() + "; ");
            System.out.print(payment.getCommission() + "; ");
            System.out.print(payment.getTotalLeft() + "; ");
            System.out.println();
        }
    }

    public static void printApplication(final CreditApplication application) {
        System.out.println("Application: ");
        System.out.println("\tAmount: " + application.getAmount());
        System.out.println("\t" + application.getDuration());

    }
}
