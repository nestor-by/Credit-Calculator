package su.ugatu.moodle.is.credit_calc;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 20.01.14
 * Time: 19:06
 */
public class CommissionTest {

    private CreditApplication application;
    private CreditOffer offer;

    @Before
    /**
     * don't change! All asserts depend on this particular values.
     */
    public void setUp() {
        application = new CreditApplicationImpl(100000d);
        application.setDurationInMonths(12);

        offer = new CreditOfferImpl();
        offer.setRate(0.1699d);
        offer.setOnceCommissionAmount(100d);
        offer.setOnceCommissionPercent(0.01d);
        offer.setMonthlyCommissionAmount(100d);
        offer.setMonthlyCommissionPercent(0.01d);
    }

    @Test
    public void annuityCommissionTest() {
        application.setPaymentType(CreditPaymentType.ANNUITY);
        CreditProposal proposal = offer.calculateProposal(application);
        assertEquals(proposal.getEffectiveRate(), 0.5068d, 0.0001d);

        TestUtil.printProposal(proposal);
    }

    @Test
    public void differentialCommissionTest() {
        application.setPaymentType(CreditPaymentType.DIFFERENTIAL);
        CreditProposal proposal = offer.calculateProposal(application);
        assertEquals(proposal.getEffectiveRate(), 0.5152d, 0.0001d);
        TestUtil.printProposal(proposal);
    }
}
