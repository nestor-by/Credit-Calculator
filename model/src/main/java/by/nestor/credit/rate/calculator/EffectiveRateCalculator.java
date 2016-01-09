package by.nestor.credit.rate.calculator;

import by.nestor.credit.payments.CreditPayment;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public interface EffectiveRateCalculator {
    /**
     * Используйте для расчета эффективной процентоной ставки по предложению.
     *
     * @return эффективаная процентная ставка в долях от единицы.
     */
    BigDecimal calculate(BigDecimal amount, BigDecimal commission, List<CreditPayment> payments);
}
