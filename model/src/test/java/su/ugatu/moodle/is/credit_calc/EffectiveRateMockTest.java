package su.ugatu.moodle.is.credit_calc;
import static org.mockito.Mockito.*;

import org.junit.Test;
import su.ugatu.moodle.is.util.CalendarUtil;
import su.ugatu.moodle.is.util.FinUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 19.01.14
 * Time: 0:21
 */
public class EffectiveRateMockTest {

    @Test
    /**
     * Example from
     */
    public void testCalcEffectiveScore() {
        CreditProposal proposal = mock(CreditProposal.class);
        when(proposal.getCreditAmount()).thenReturn(1000d);

        ArrayList<CreditPayment> payments = new ArrayList<CreditPayment>();
        Date date = new Date();
        payments.add(new CreditPaymentImpl(600d, date = CalendarUtil.nextMonthDate(date)));
        payments.add(new CreditPaymentImpl(0d, date = CalendarUtil.nextMonthDate(date)));
        payments.add(new CreditPaymentImpl(310d, date = CalendarUtil.nextMonthDate(date)));
        payments.add(new CreditPaymentImpl(194.25d, CalendarUtil.nextMonthDate(date)));
        when(proposal.getPayments()).thenReturn(payments);
        Double rate = FinUtil.calcEffectiveRate(proposal);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println("Rate: " + decimalFormat.format(rate*100) + "%");
    }
}