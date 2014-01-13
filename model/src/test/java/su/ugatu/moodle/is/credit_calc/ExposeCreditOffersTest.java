package su.ugatu.moodle.is.credit_calc;

import org.junit.Test;

import java.util.List;

/**
 * User: Rinat
 * Date: 13.01.14
 * Time: 23:27
 */
public class ExposeCreditOffersTest {

    @Test
    public void exposeBankOffers() {
        List<LendingBank> banks = BankFactory.getInstance().getBanks();
        for (LendingBank bank: banks) {
            System.out.println(bank.getName() + ". Default currency:  "
                                + bank.getDefaultCurrency() + ".");
            List<CreditOffer> creditOffers = bank.getCreditOffers();
            for (CreditOffer creditOffer: creditOffers) {
                System.out.print("\t" + creditOffer.getName() + ": ");
                System.out.print(creditOffer.getMinAmount() + " - ");
                System.out.print(creditOffer.getMaxAmount() + " ");
                System.out.print(creditOffer.getCurrency() + ". ");
                System.out.print("Min rate: " + creditOffer.getMinRate() + ".");
                System.out.println();
            }
        }
    }
}
