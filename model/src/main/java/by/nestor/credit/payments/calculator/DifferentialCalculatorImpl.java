package by.nestor.credit.payments.calculator;

import by.nestor.credit.Constants;
import by.nestor.credit.payments.CreditPayment;
import by.nestor.credit.payments.CreditPaymentImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class DifferentialCalculatorImpl implements PaymentsCalculator {

    /**
     * Рассчитать платежи по кредиту по дифференцированной схеме.
     *
     * @param creditAmount      размер кредита.
     * @param durationInMonths  продолжительность в месяцах.
     * @param date              дата.
     * @param rate              ставка кредита в долях от единицы.
     * @param monthlyCommission ежемесячная комиссия.
     * @return упорядоченный по дате список платежей по кредиту.
     */
    public List<CreditPayment> calculate(final BigDecimal creditAmount,
                                         final int durationInMonths,
                                         LocalDate date,
                                         final BigDecimal rate,
                                         final BigDecimal monthlyCommission) {
        //Формула расчета платежа по дифференцированной схеме
        //A = (1 / n + L / 12) * S - (S * L / 12) / n * (k - 1)
        //amount = (1 / durationInMonths + monthlyRate) * creditAmount
        // - (creditAmount * monthlyRate / durationInMonthsBD) * (k - 1);
        ArrayList<CreditPayment> payments = new ArrayList<CreditPayment>();
        //Ежемесячная процентная ставка по кредиту   L / 12
        BigDecimal monthlyRate = rate.divide(
                new BigDecimal(Constants.NUMBER_OF_MONTHS),
                Constants.CALC_SCALE, Constants.ROUNDING_MODE);
        BigDecimal durationInMonthsBD = new BigDecimal(durationInMonths);
        //Устанавливаем остаток равным размеру кредита
        BigDecimal base = creditAmount;

        for (int k = 1; k <= durationInMonths; k++) {
            //процентная часть платежа
            BigDecimal interest = base.multiply(monthlyRate);
            //добавляем к остатку
            base = base.add(interest);
            //первое слагаемое в формуле
            //(1 / n + L / 12) * S
            BigDecimal firstSum = BigDecimal.ONE
                    .divide(durationInMonthsBD,
                            Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE);

            firstSum = firstSum.add(monthlyRate).multiply(creditAmount);
            //первое слагаемое в формуле
            //(S * L / 12) / n * (k - 1)
            BigDecimal secondSum = creditAmount
                    .multiply(monthlyRate)
                    .divide(durationInMonthsBD,
                            Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE)
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
            date = date.plusMonths(1);
            CreditPayment payment
                    = new CreditPaymentImpl(Constants.round(withCommAmount, Constants.CALC_SCALE),
                    date);
            //долговая часть платежа: creditAmount/durationInMonths
            payment.setDebt(Constants.round(creditAmount
                    .divide(durationInMonthsBD,
                            Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE), Constants.OUTPUT_AMOUNT_SCALE));
            //вычитаем от остатка размер платежа
            base = base.subtract(amount);

            //процентная часть платежа
            payment.setInterest(Constants.round(interest, Constants.OUTPUT_AMOUNT_SCALE));
            //остаток на следующий месяц
            payment.setTotalLeft(Constants.round(base, Constants.OUTPUT_AMOUNT_SCALE));
            //размер ежемесячной комиссии
            if (monthlyCommission != null) {
                payment.setCommission(Constants.round(monthlyCommission, Constants.CALC_SCALE));
            }
            //добавляем данный платеж в список платежей
            payments.add(payment);
        }
        //возвращаем список платежей
        return payments;
    }
}
