package by.nestor.credit.rate.calculator;

import by.nestor.credit.payments.CreditPayment;

import java.math.BigDecimal;
import java.util.List;

import static by.nestor.credit.Constants.*;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class NewtonsMethodCalculator implements EffectiveRateCalculator {
    /**
     * Используйте для расчета эффективной процентоной ставки по предложению.
     *
     * @return эффективаная процентная ставка в долях от единицы.
     */
    @Override
    public BigDecimal calculate(BigDecimal amount, BigDecimal commission, List<CreditPayment> payments) {
        // Метод Ньютона (касательных)
        final double eps = 0.00001d; // погрешность
        // начальное значение множителя дисконирования
        double x = 1d;
        double currentEps = 1d;    // текущая погрешность
        double er = 0d;              // эффективная процентная ставка
        final int paymentCount = payments.size();

        while (currentEps > eps) {

            // 1. Инициализация переменных
            double fx = 0d;
            double dfx = 0d;

            // 2. Цикл для вычисления сумм в функции и производной в точке х
            for (int i = 1; i <= paymentCount; i++) {
                CreditPayment payment = payments.get(i - 1);
                fx += payment.getAmount().doubleValue() * Math.pow(x, i);
                dfx += i * payment.getAmount().doubleValue() * Math.pow(x, i - 1);
            }

            // 3. Для функции f(x) вычтем размер кредита
            fx -= amount.doubleValue();
            //    и добавим первоначальную комиссию
            if (commission != null) {
                fx += commission.doubleValue();
            }

            // 4. Шаг при поиске корня методом Ньютона
            x -= fx / dfx;

            // 5. Расчет погрешности - разница между предыдущим значением
            //    ЭПС и вычисленным.
            double previousER = er;
            er = Math.pow(x, -NUMBER_OF_MONTHS) - 1;
            currentEps = Math.abs(previousER - er);
        }

        return round(new BigDecimal(er), OUTPUT_PERCENT_SCALE);
    }
}
