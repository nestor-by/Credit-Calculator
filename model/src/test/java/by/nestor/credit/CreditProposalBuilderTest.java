package by.nestor.credit;

import by.nestor.credit.payments.CreditPayment;
import by.nestor.credit.payments.CreditPaymentType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

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
        assertEquals(Constants.round(proposal.getEffectiveRate(), Constants.OUTPUT_PERCENT_SCALE), new BigDecimal(0.1838));
        assertEquals(Constants.round(proposal.getTotalPayment(), Constants.OUTPUT_AMOUNT_SCALE), new BigDecimal(109440.01));
        for (CreditPayment payment : payments) {
            assertEquals(Constants.round(payment.getAmount(), Constants.OUTPUT_AMOUNT_SCALE), new BigDecimal(9120));
        }

    }
}