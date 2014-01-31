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

        // Метод Ньютона (касательных)
        final double eps = 0.00001d; // погрешность
        // начальное значение множителя дисконирования
        double x = 1d;
        double currentEps = 1.0d;    // текущая погрешность
        double er = 0d;              // эффективная процентная ставка

        while (currentEps > eps) {

            // 1. Инициализация переменных
            double fx = 0d;
            double dfx = 0d;
            final int paymentCount = proposal.getPayments().size();

            // 2. Цикл для вычисления сумм в функции и производной в точке х
            for (int i = 1; i <= paymentCount; i++) {
                CreditPayment payment = proposal.getPayments().get(i - 1);
                fx += payment.getAmount().doubleValue() * Math.pow(x, i);
                dfx += i*payment.getAmount().doubleValue() * Math.pow(x, i - 1);
            }

            // 3. Для функции f(x) вычтем размер кредита
            fx -= proposal.getCreditAmount().doubleValue();
            //    и добавим первоначальную комиссию
            if (proposal.getInitialCreditCommission() != null) {
                fx += proposal.getInitialCreditCommission().doubleValue();
            }

            // 4. Шаг при поиске корня методом Ньютона
            x -= fx / dfx;

            // 5. Расчет погрешности - разница между предыдущим значением
            //    ЭПС и вычисленным.
            double previousER = er;
            er = Math.pow(x, -CalendarUtil.NUMBER_OF_MONTHS) - 1;
            currentEps = Math.abs(previousER - er);
        }

        return new BigDecimal(er).setScale(Constants.OUTPUT_PERCENT_SCALE,
                                           Constants.ROUNDING_MODE);
    }
}
