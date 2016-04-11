package by.nestor.credit.payments.calculator;

import by.nestor.credit.Constants;
import by.nestor.credit.Duration;
import by.nestor.credit.payments.CreditPayment;
import by.nestor.credit.payments.CreditPaymentImpl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class AnnuityCalculatorImpl implements PaymentsCalculator {
    /**
     * Рассчитать платежи по кредиту по аннуитетной схеме.
     *
     * @param creditAmount      размер кредита.
     * @param duration  продолжительность в месяцах.
     * @param date              дата.
     * @param rate              ставка кредита в долях от единицы.
     * @param monthlyCommission ежемесячная комиссия.
     * @return упорядоченный по дате список платежей по кредиту.
     */
    public List<CreditPayment> calculate(final BigDecimal creditAmount,
                                         final Duration duration,
                                         LocalDate date,
                                         final BigDecimal rate,
                                         final BigDecimal monthlyCommission) {
        ArrayList<CreditPayment> payments = new ArrayList<CreditPayment>();
        // Формула расчета платежа по аннуитетной схеме A = (L / 12) / (1 - (1 + (l / 12)) ^ -n )
        // месячная ставка по кредиту   L / 12
        BigDecimal monthlyRate = rate.multiply(BigDecimal.valueOf(duration.frequency.days()));

        //  1 + (l / 12) ^ -n
        BigDecimal pow = monthlyRate.add(BigDecimal.ONE).pow(-duration.value, MathContext.DECIMAL64);

        BigDecimal denominator = BigDecimal.ONE;


        denominator = denominator.subtract(pow);

        //Расчет суммы платежа без комиссии
        BigDecimal amount = creditAmount.multiply(monthlyRate).divide(
                denominator,
                Constants.CALC_SCALE,
                Constants.ROUNDING_MODE
        );

        BigDecimal withCommAmount = amount;

        //Проверка на месячную комиссию, если существует, то учитываем
        if (monthlyCommission != null) {
            withCommAmount = withCommAmount.add(monthlyCommission);// Сумма платежа с комиссией
        }

        BigDecimal base = creditAmount; //Сумма кредита

        for (int i = 0; i < duration.value; i++) {
            //Начисленные проценты
            BigDecimal interest = base.multiply(monthlyRate);

            //Создаем объект платежа по кредиту с суммой платежа с учетом комисии, если она существует.
            BigDecimal paymentAmount = Constants.round(withCommAmount, Constants.CALC_SCALE);
            CreditPayment payment = new CreditPaymentImpl(paymentAmount, date = duration.frequency.next(date));

            //Добавляем и округляем Основной долг, разница между суммой платежа и начисленными процентами
            payment.setDebt(Constants.round(amount.subtract(interest), Constants.CALC_SCALE));

            //Добавляем и Округляем остаток с начисленными процентами
            payment.setInterest(Constants.round(interest));
            //Суммируем остаток с начисленными процентами
            base = base.add(interest);
            // Расчитываем остаток
            base = base.subtract(amount);

            //Добавляем Остаток на следующий месяц
            payment.setTotalLeft(Constants.round(base, Constants.CALC_SCALE));

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
