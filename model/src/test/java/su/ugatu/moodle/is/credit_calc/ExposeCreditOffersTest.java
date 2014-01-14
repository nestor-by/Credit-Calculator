package su.ugatu.moodle.is.credit_calc;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 23:27
 */
public class ExposeCreditOffersTest {

    @Test
    public void exposeBankOffers() {
        Set<LendingBank> banks = new HashSet<LendingBank>
                                        (BankFactory.getInstance().getBanks());
        for (LendingBank bank: banks) {
            System.out.println(bank.getName() + ". Default currency:  "
                                + bank.getDefaultCurrency() + ".");
            Set<CreditOffer> creditOffers = new HashSet<CreditOffer>
                                                    (bank.getCreditOffers());
            for (CreditOffer creditOffer: creditOffers) {
                System.out.print("\t" + creditOffer.getName() + ": ");
                System.out.print(creditOffer.getMinAmount() + " - ");
                System.out.print(creditOffer.getMaxAmount() + " ");
                System.out.print(creditOffer.getCurrency() + ". ");
                System.out.print("Min rate: " + creditOffer.getRate() + ". ");
                System.out.print(creditOffer.getMinMonthDuration() + " - ");
                System.out.print(creditOffer.getMaxMonthDuration() + " months. ");
                System.out.println();
            }
        }
    }
}
