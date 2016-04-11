package by.nestor.credit;

import by.nestor.credit.commission.Commission;
import by.nestor.credit.payments.CreditPaymentType;
import org.junit.Test;

import java.math.BigDecimal;

import static by.nestor.credit.TimeUnit.MONTHLY;
import static by.nestor.credit.commission.CommissionType.AMOUNT;
import static by.nestor.credit.commission.CommissionType.PERCENT;
import static org.junit.Assert.assertEquals;

/**
 * Created: iiinjoy@gmail.com
 * Date: 31.01.14
 * Time: 18:36
 */

public class ThreeYearEPR {

    @Test
    public void calcThreeYearEPR() {
        CreditOffer offer = new CreditOfferImpl().setRate(new BigDecimal("0.155"))
                .setCommissions(
                        new Commission(AMOUNT, new BigDecimal("3000")),
                        new Commission(PERCENT, new BigDecimal("0.015")),
                        new Commission(AMOUNT, new Frequency(1, MONTHLY), new BigDecimal("100")),
                        new Commission(PERCENT, new Frequency(1, MONTHLY), new BigDecimal("0.005"))
                );

        CreditApplication app = new CreditApplicationImpl(new BigDecimal("300000"))
                .setDurations(new Duration(36, new Frequency(1, MONTHLY), CreditPaymentType.ANNUITY));

        CreditProposal proposal = offer.calculateProposal(app);
        assertEquals(proposal.getEffectiveRate(), new BigDecimal("0.3168"));
    }
}
