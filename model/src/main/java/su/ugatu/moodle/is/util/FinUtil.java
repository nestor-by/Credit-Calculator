package su.ugatu.moodle.is.util;

import su.ugatu.moodle.is.credit_calc.CreditPayment;
import su.ugatu.moodle.is.credit_calc.CreditProposal;

import java.math.BigDecimal;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 18.01.14
 * Time: 23:30
 */
public class FinUtil {

    /**
     * Используйте для расчета эффективной процентоной ставки по предложению.
     *
     * @param proposal кредитное предложение.
     * @return эффективаная процентная ставка в долях от единицы.
     */
    public static BigDecimal calcEffectiveRate(CreditProposal proposal) {
        // Newton method
        final double eps = 0.00001d; // погрешность
        double x = 1d;
        double currentEps = 1.0d;
        double er = 0d; // effective rate

        while (currentEps > eps) {
            double fx = 0d;
            double dfx = 0d;
            final int paymentCount = proposal.getPayments().size();

            for (int i = 1; i <= paymentCount; i++) {
                CreditPayment payment = proposal.getPayments().get(i - 1);
                fx += payment.getAmount().doubleValue() * Math.pow(x, i);
                dfx += i * payment.getAmount().doubleValue() * Math.pow(x, i - 1);
            }
            fx -= proposal.getCreditAmount().doubleValue();
            if (proposal.getInitialCreditCommission() != null) {
                fx += proposal.getInitialCreditCommission().doubleValue();
            }
            x -= fx / dfx;
            double previousER = er;
            er = Math.pow(x, -paymentCount) - 1;
            currentEps = Math.abs(previousER - er);
        }
        return new BigDecimal(er).setScale(Constants.OUTPUT_PERCENT_SCALE, Constants.ROUNDING_MODE);
    }
}
