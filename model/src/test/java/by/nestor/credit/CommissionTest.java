package by.nestor.credit;

import by.nestor.credit.commission.Commission;
import by.nestor.credit.payments.CreditPaymentType;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static by.nestor.credit.commission.CommissionType.AMOUNT;
import static by.nestor.credit.commission.CommissionType.PERCENT;
import static org.junit.Assert.assertEquals;

/**
 * @author rinat.enikeev@gmail.com
 *         Date: 20.01.14
 *         Time: 19:06
 */
public class CommissionTest {

    private CreditApplication application;
    private CreditOffer offer;

    @Before
    /**
     * don't change! All asserts depend on this particular values.
     */
    public void setUp() {
        BigDecimal amount = new BigDecimal(100000).setScale(Constants.OUTPUT_AMOUNT_SCALE);
        application = new CreditApplicationImpl(amount);

        offer = new CreditOfferImpl();
        offer.setRate(new BigDecimal("0.1699").setScale(Constants.OUTPUT_PERCENT_SCALE));
        offer.setCommissions(
                new Commission(AMOUNT, new BigDecimal("100").setScale(Constants.OUTPUT_AMOUNT_SCALE)),
                new Commission(PERCENT, new BigDecimal("0.01").setScale(Constants.OUTPUT_PERCENT_SCALE)),
                new Commission(AMOUNT, new Frequency(1, TimeUnit.MONTHLY), new BigDecimal("100").setScale(Constants.OUTPUT_AMOUNT_SCALE)),
                new Commission(PERCENT, new Frequency(1, TimeUnit.MONTHLY), new BigDecimal("0.01").setScale(Constants.OUTPUT_PERCENT_SCALE))
        );
    }

    @Test
    public void annuityCommissionTest() {
        application.setDurations(new Duration(12, new Frequency(1, TimeUnit.MONTHLY), CreditPaymentType.ANNUITY));
        TestUtil.printApplication(application);
        CreditProposal proposal = offer.calculateProposal(application);
        assertEquals(proposal.getEffectiveRate(), (new BigDecimal("0.5068")));

        TestUtil.printProposal(proposal);
    }

    @Test
    public void differentialCommissionTest() {
        application.setDurations(new Duration(12, new Frequency(1, TimeUnit.MONTHLY), CreditPaymentType.DIFFERENTIAL));
        CreditProposal proposal = offer.calculateProposal(application);
        assertEquals(proposal.getEffectiveRate(), new BigDecimal("0.5152"));
        TestUtil.printProposal(proposal);
    }
}
