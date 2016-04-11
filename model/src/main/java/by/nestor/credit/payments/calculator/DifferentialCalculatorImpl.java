package by.nestor.credit.payments.calculator;

import by.nestor.credit.Duration;
import by.nestor.credit.payments.CreditPayment;
import by.nestor.credit.payments.CreditPaymentImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.nestor.credit.Constants.*;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class DifferentialCalculatorImpl implements PaymentsCalculator {

    /**
     * Рассчитать платежи по кредиту по дифференцированной схеме.
     *
     * @param creditAmount      размер кредита.
     * @param duration          продолжительность в месяцах.
     * @param date              дата.
     * @param rate              ставка кредита в долях от единицы.
     * @param monthlyCommission ежемесячная комиссия.
     * @return упорядоченный по дате список платежей по кредиту.
     */
    public List<CreditPayment> calculate(final BigDecimal creditAmount,
                                         final Duration duration,
                                         final LocalDate startDate,
                                         final BigDecimal rate,
                                         final BigDecimal monthlyCommission) {
        //Формула расчета платежа по дифференцированной схеме
        //A = (1 / n + L / 12) * S - (S * L / 12) / n * (k - 1)
        //amount = (1 / durationInMonths + monthlyRate) * creditAmount
        // - (creditAmount * monthlyRate / durationInMonthsBD) * (k - 1);
        ArrayList<CreditPayment> payments = new ArrayList<CreditPayment>();
        //Ежемесячная процентная ставка по кредиту   L / 12

        //Устанавливаем остаток равным размеру кредита
        BigDecimal base = creditAmount;

        LocalDate date;
        for (int k = 1; k <= duration.value; k++) {
            date = duration.frequency.next(startDate);
            BigDecimal durationInMonthsBD = BigDecimal.valueOf(duration.frequency.days(date));
            BigDecimal durationRate = rate.multiply(durationInMonthsBD);
            //процентная часть платежа
            BigDecimal interest = base.multiply(durationRate);
            //добавляем к остатку
            base = base.add(interest);
            //первое слагаемое в формуле
            //(1 / n + L / 12) * S
            BigDecimal firstSum = BigDecimal.ONE
                    .divide(durationInMonthsBD,
                            CALC_SCALE,
                            ROUNDING_MODE);

            firstSum = firstSum.add(durationRate).multiply(creditAmount);
            //первое слагаемое в формуле
            //(S * L / 12) / n * (k - 1)
            BigDecimal secondSum = creditAmount
                    .multiply(rate)
                    .multiply(new BigDecimal(k - 1));
            //второе слагаемое в формуле
            //будет вычислено в 0 для первого платежа, так как k-1=0 при k=1
            //A = (1 / n + L / 12) * S - (S * L / 12) / n * (k - 1)
            BigDecimal amount = firstSum.subtract(secondSum);

            //Если есть ежемесячная комиссия, учесть ее в размере платежа
            BigDecimal withCommAmount = amount;
            if (monthlyCommission != null) {
                withCommAmount = withCommAmount.add(monthlyCommission);
            }

            //следующая дата платежа
            CreditPayment payment = new CreditPaymentImpl(round(withCommAmount, CALC_SCALE), date);
            //долговая часть платежа: creditAmount/durationInMonths
            payment.setDebt(round(creditAmount.divide(durationInMonthsBD, CALC_SCALE, ROUNDING_MODE), OUTPUT_AMOUNT_SCALE));
            //вычитаем от остатка размер платежа
            base = base.subtract(amount);

            //процентная часть платежа
            payment.setInterest(round(interest, OUTPUT_AMOUNT_SCALE));
            //остаток на следующий месяц
            payment.setTotalLeft(round(base, OUTPUT_AMOUNT_SCALE));
            //размер ежемесячной комиссии
            if (monthlyCommission != null) {
                payment.setCommission(round(monthlyCommission, CALC_SCALE));
            }
            //добавляем данный платеж в список платежей
            payments.add(payment);
        }
        //возвращаем список платежей
        return payments;
    }
}
