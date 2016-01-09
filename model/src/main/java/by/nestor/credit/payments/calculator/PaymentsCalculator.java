package by.nestor.credit.payments.calculator;

import by.nestor.credit.payments.CreditPayment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public interface PaymentsCalculator {
    List<CreditPayment> calculate(final BigDecimal creditAmount,
                                  final int durationInMonths,
                                  LocalDate date,
                                  final BigDecimal rate,
                                  final BigDecimal monthlyCommission);
}
