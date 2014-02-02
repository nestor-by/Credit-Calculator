package su.ugatu.moodle.is.credit_calc;

import su.ugatu.moodle.is.util.CalendarUtil;
import su.ugatu.moodle.is.util.Constants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 18.01.14
 * Time: 23:30
 */
class FinUtil {

    /**
     * Рассчитать платежи по кредиту по аннуитетной схеме.
     *
     * @param creditAmount размер кредита.
     * @param durationInMonths продолжительность в месяцах.
     * @param date дата.
     * @param rate ставка кредита в долях от единицы.
     * @param monthlyCommission ежемесячная комиссия.
     * @return упорядоченный по дате список платежей по кредиту.
     */
    static List<CreditPayment> calcAnnuityPayments
                                    (final BigDecimal creditAmount,
                                     final int durationInMonths,
                                     Date date,
                                     final BigDecimal rate,
                                     final BigDecimal monthlyCommission) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    static List<CreditPayment> calcDifferentialPayments(final BigDecimal creditAmount,
                                          final int durationInMonths,
                                          Date date,
                                          final BigDecimal rate,
                                          final BigDecimal monthlyCommission) {

        ArrayList<CreditPayment> payments = new ArrayList<CreditPayment>();

        BigDecimal monthlyRate = rate.divide(
                new BigDecimal(CalendarUtil.NUMBER_OF_MONTHS),
                Constants.CALC_SCALE, Constants.ROUNDING_MODE);
        BigDecimal durationInMonthsBD = new BigDecimal(durationInMonths);
        BigDecimal base = creditAmount;

        for (int k = 1; k <= durationInMonths; k++) {
            BigDecimal interest = base.multiply(monthlyRate);
            base = base.add(interest);

            BigDecimal firstSum = new BigDecimal(1)
                    .divide(durationInMonthsBD,
                            Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE);

            firstSum = firstSum.add(monthlyRate).multiply(creditAmount);

            BigDecimal secondSum = creditAmount
                    .multiply(monthlyRate)
                    .divide(durationInMonthsBD,
                            Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE)
                    .multiply(new BigDecimal(k - 1));

            BigDecimal amount = firstSum.subtract(secondSum);

            BigDecimal withCommAmount = amount;
            if (monthlyCommission != null) {
                withCommAmount = withCommAmount.add(monthlyCommission);
            }

            date = CalendarUtil.nextMonthDate(date);
            CreditPayment payment
                    = new CreditPaymentImpl(withCommAmount
                    .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                            Constants.ROUNDING_MODE),
                    date);
            payment.setDebt(creditAmount
                    .divide(durationInMonthsBD,
                            Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE)
                    .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                            Constants.ROUNDING_MODE));

            base = base.subtract(amount);
            payment.setInterest(interest
                    .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                            Constants.ROUNDING_MODE));

            payment.setTotalLeft(base
                    .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                            Constants.ROUNDING_MODE));
            if (monthlyCommission != null) {
                payment.setCommission(monthlyCommission
                        .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                                Constants.ROUNDING_MODE));
            }
                    payments.add(payment);
        }

        return payments;
    }

    static BigDecimal calcTotalAmount(final List<CreditPayment> payments) {
        BigDecimal totalAmount = new BigDecimal(0);
        for (CreditPayment payment : payments) {
            totalAmount = totalAmount.add(payment.getAmount());
        }
        return totalAmount.setScale(Constants.OUTPUT_AMOUNT_SCALE,
                Constants.ROUNDING_MODE);
    }


    /**
     * Рассчитывает ежемесячную комиссию по офферу.
     *
     * @param creditAmount размер кредита.
     * @param creditOffer оффер.
     * @return ежемесячная комиссия.
     */
    static BigDecimal calcMonthlyCommission(final BigDecimal creditAmount,
                                            final CreditOffer creditOffer) {

        BigDecimal monCommAmount = creditOffer.getMonthlyCommissionAmount();
        BigDecimal monCommPercent = creditOffer.getMonthlyCommissionPercent();

        BigDecimal monComm = new BigDecimal(0)
                .setScale(Constants.OUTPUT_AMOUNT_SCALE);
        if (monCommAmount != null) {
            monComm = monComm.add(monCommAmount);
        }
        if (monCommPercent != null) {
            monComm = monComm.add(creditAmount.multiply(monCommPercent));
        }
        return monComm.setScale(Constants.OUTPUT_AMOUNT_SCALE);
    }

    /**
     * Рассчитывает изначальную комиссию (не входит в ежемесячные платежи).
     *
     * @param creditAmount размер кредита.
     * @param creditOffer оффер.
     * @return начальная комиссия (не входит в ежемесячные платежи).
     */
    static BigDecimal calcInitialCommission(final BigDecimal creditAmount,
                                            final CreditOffer creditOffer) {

        BigDecimal initCommPay = new BigDecimal("0")
                .setScale(Constants.OUTPUT_AMOUNT_SCALE);
        BigDecimal onceCommAmount = creditOffer.getOnceCommissionAmount();
        if (onceCommAmount != null) {
            initCommPay = initCommPay.add(onceCommAmount);
        }
        BigDecimal onceCommPrcnt = creditOffer.getOnceCommissionPercent();
        if (onceCommPrcnt != null) {
            initCommPay = initCommPay.add(creditAmount.multiply(onceCommPrcnt));
        }
        return initCommPay.setScale(Constants.OUTPUT_AMOUNT_SCALE);
    }

    /**
     * Используйте для расчета эффективной процентоной ставки по предложению.
     *
     * @param proposal кредитное предложение.
     * @return эффективаная процентная ставка в долях от единицы.
     */
    static BigDecimal calcEffectiveRate(CreditProposal proposal) {

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
