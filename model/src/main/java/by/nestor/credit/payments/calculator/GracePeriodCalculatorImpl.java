package by.nestor.credit.payments.calculator;

import by.nestor.credit.Constants;
import by.nestor.credit.Duration;
import by.nestor.credit.payments.CreditPayment;
import by.nestor.credit.payments.CreditPaymentImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class GracePeriodCalculatorImpl implements PaymentsCalculator {
    /**
     * Рассчитать платежи по кредиту по аннуитетной схеме.
     *
     * @param creditAmount      размер кредита.
     * @param duration          продолжительность в месяцах.
     * @param startDate         дата.
     * @param rate              ставка кредита в долях от единицы.
     * @param monthlyCommission ежемесячная комиссия.
     * @return упорядоченный по дате список платежей по кредиту.
     */
    public List<CreditPayment> calculate(final BigDecimal creditAmount,
                                         final Duration duration,
                                         final LocalDate startDate,
                                         final BigDecimal rate,
                                         final BigDecimal monthlyCommission) {
        List<CreditPayment> payments = new ArrayList<>();
        LocalDate date;
        for (int i = 0; i < duration.value; i++) {
            date = duration.frequency.next(startDate);
            BigDecimal durationInMonthsBD = BigDecimal.valueOf(duration.frequency.days(date));
            BigDecimal durationRate = rate.multiply(durationInMonthsBD);
            BigDecimal amount = creditAmount.multiply(durationRate);
            CreditPayment payment = new CreditPaymentImpl(amount, date);
            payment.setDebt(BigDecimal.ZERO);
            payment.setInterest(amount);
            payment.setTotalLeft(Constants.round(creditAmount, Constants.CALC_SCALE));

            //Если комиссия существует, то добавляем её
            if (monthlyCommission != null) {
                payment.setCommission(Constants.round(monthlyCommission, Constants.CALC_SCALE));
            }

            //Прибавляем записи
            payments.add(payment);
        }
        return payments;
    }


}
