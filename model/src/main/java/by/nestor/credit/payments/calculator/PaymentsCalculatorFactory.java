package by.nestor.credit.payments.calculator;

import by.nestor.credit.payments.CreditPaymentType;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class PaymentsCalculatorFactory {
    public static PaymentsCalculator factory(CreditPaymentType type) {
        switch (type) {
            case ANNUITY: {
                return new AnnuityCalculatorImpl();
            }
            case DIFFERENTIAL: {
                return new DifferentialCalculatorImpl();
            }
            default:
                throw new UnsupportedOperationException(type + " type is not supported");
        }

    }
}
