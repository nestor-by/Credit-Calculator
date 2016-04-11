package by.nestor.credit;

import by.nestor.credit.payments.CreditPayment;
import by.nestor.credit.payments.CreditPaymentType;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static by.nestor.credit.Constants.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class CreditProposalBuilderTest {

    @Test
    public void testBuild() throws Exception {
        CreditOffer offer = new CreditOfferImpl();
        offer.setRate(new BigDecimal("0.1699"));
        CreditApplication app = new CreditApplicationImpl(new BigDecimal("100000"));
        app.setDurations(new Duration(12, new Frequency(1, TimeUnit.MONTHLY), CreditPaymentType.ANNUITY));
        CreditProposal proposal = offer.calculateProposal(app);
        TestUtil.printProposal(proposal);

        List<CreditPayment> payments = proposal.getPayments();
        assertScaleEquals(proposal.getEffectiveRate(), new BigDecimal(0.1838), OUTPUT_PERCENT_SCALE);
        assertScaleEquals(proposal.getTotalPayment(), new BigDecimal(109440.01), OUTPUT_AMOUNT_SCALE);
        BigDecimal actual = new BigDecimal(9120.00);
        for (CreditPayment payment : payments) {
            assertScaleEquals(payment.getAmount(), actual, OUTPUT_AMOUNT_SCALE);
        }
    }

    @Test
    public void testBuild2() throws Exception {
        CreditOffer offer = new CreditOfferImpl();
        offer.setRate(new BigDecimal("0.005000"));
        CreditApplication application = new CreditApplicationImpl(new BigDecimal("2900.00"));
        application.setDurations(
                new Duration(1, new Frequency(4, TimeUnit.WEEKLY), CreditPaymentType.GRACE_PERIOD),
                new Duration(4, new Frequency(2, TimeUnit.WEEKLY), CreditPaymentType.ANNUITY),
                new Duration(4, new Frequency(2, TimeUnit.WEEKLY), CreditPaymentType.DIFFERENTIAL)
        );
        application.setStartDate(LocalDate.parse("2016-01-08"));
        CreditProposal proposal = offer.calculateProposal(application);
        TestUtil.printProposal(proposal);
    }

    private void assertScaleEquals(BigDecimal expected, BigDecimal actual, int scale) {
        assertEquals(round(expected, scale), round(actual, scale));
    }
}