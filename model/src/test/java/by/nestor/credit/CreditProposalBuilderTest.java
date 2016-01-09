package by.nestor.credit;

import by.nestor.credit.payments.CreditPayment;
import by.nestor.credit.payments.CreditPaymentType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static by.nestor.credit.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class CreditProposalBuilderTest {

    @Test
    public void testBuild() throws Exception {
        CreditOffer offer = new CreditOfferImpl();
        offer.setRate(new BigDecimal("0.1699"));
        CreditApplication app = new CreditApplicationImpl(new BigDecimal("100000"));
        app.setDuration(12);
        app.setPaymentType(CreditPaymentType.ANNUITY);
        CreditProposal proposal = offer.calculateProposal(app);
        TestUtil.printProposal(proposal);

        List<CreditPayment> payments = proposal.getPayments();
        assertTrue(payments.size() == app.getDuration());
        assertScaleEquals(proposal.getEffectiveRate(), new BigDecimal(0.1838), OUTPUT_PERCENT_SCALE);
        assertScaleEquals(proposal.getTotalPayment(), new BigDecimal(109440.01), OUTPUT_AMOUNT_SCALE);
        BigDecimal actual = new BigDecimal(9120.00);
        for (CreditPayment payment : payments) {
            assertScaleEquals(payment.getAmount(), actual, OUTPUT_AMOUNT_SCALE);
        }
    }

    private void assertScaleEquals(BigDecimal expected, BigDecimal actual, int scale) {
        assertEquals(round(expected, scale), round(actual, scale));
    }
}