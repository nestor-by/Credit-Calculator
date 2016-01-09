package by.nestor.credit.rate.calculator;

import by.nestor.credit.payments.CreditPayment;
import by.nestor.credit.payments.CreditPaymentImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuryvoschilo on 1/9/16.
 */
public class NewtonsMethodCalculatorTest {

    @Test
    public void testCalculate() throws Exception {
        List<CreditPayment> payments = new ArrayList<>();
        LocalDate date = LocalDate.now();
        payments.add(new CreditPaymentImpl(new BigDecimal(600d), date = date.plusMonths(1)));
        payments.add(new CreditPaymentImpl(new BigDecimal(0d), date = date.plusMonths(1)));
        payments.add(new CreditPaymentImpl(new BigDecimal(310d), date = date.plusMonths(1)));
        payments.add(new CreditPaymentImpl(new BigDecimal(194.25d), date.plusMonths(1)));
        BigDecimal rate = new NewtonsMethodCalculator().calculate(new BigDecimal(1000d), null, payments);
        System.out.println("Rate: " + rate);
    }
}