package by.nestor.credit;

import by.nestor.credit.payments.CreditPayment;
import by.nestor.credit.payments.calculator.PaymentsCalculator;
import by.nestor.credit.payments.calculator.PaymentsCalculatorFactory;
import by.nestor.credit.rate.calculator.EffectiveRateCalculator;
import by.nestor.credit.rate.calculator.NewtonsMethodCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.nestor.credit.Constants.OUTPUT_AMOUNT_SCALE;
import static by.nestor.credit.Constants.round;


public class CreditProposalBuilder {
    private final CreditOffer creditOffer;
    private final EffectiveRateCalculator effectiveRateCalculator = new NewtonsMethodCalculator();

    /**
     * @param creditOffer оффер на кредит
     */
    public CreditProposalBuilder(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }

    /**
     * Создание кредита
     *
     * @param application заявка на кредит
     */
    public CreditProposal build(final CreditApplication application) {

        // 1. Проверка заполненности свойств заявки на кредит
        checkCreditApplication(application);

        // 2. Заполняем свойства предложения
        BigDecimal creditAmount = application.getAmount();
        BigDecimal initCredComm = creditOffer.getCommissions().stream()
                .filter(x -> x.getFrequency() == null)
                .map(x -> x.calculate(application))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal monthlyCommission = creditOffer.getCommissions().stream()
                .filter(x -> x.getFrequency() == creditOffer.getFrequency())
                .map(x -> x.calculate(application))
                .reduce(BigDecimal.ZERO, BigDecimal::add);//TODO create commission mechanism
        LocalDate startDate = application.getStartDate();
        LocalDate valueDate = startDate;
        List<CreditPayment> payments = new ArrayList<>();
        BigDecimal totalCredComm = initCredComm;
        for (Duration duration : application.getDurations()) {
            PaymentsCalculator calculator = PaymentsCalculatorFactory.factory(duration.paymentType);
            List<CreditPayment> calculated = calculator.calculate(
                    application.getAmount(),
                    duration,
                    valueDate,
                    creditOffer.getRate(),
                    monthlyCommission);
            valueDate = calculated
                    .stream()
                    .map(CreditPayment::getDate)
                    .max(LocalDate::compareTo)
                    .orElse(startDate);
            payments.addAll(calculated);
        }
        totalCredComm = totalCredComm.add(monthlyCommission.multiply(BigDecimal.valueOf(valueDate.until(startDate).getDays())));
        BigDecimal totalPayment = calcTotalAmount(payments).add(initCredComm);
        BigDecimal effectiveRate = effectiveRateCalculator.calculate(creditAmount, initCredComm, payments);
        return new CreditProposalImpl(creditAmount, effectiveRate, totalPayment, payments, initCredComm, totalCredComm);
    }

    private BigDecimal calcTotalAmount(final List<CreditPayment> payments) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CreditPayment payment : payments) {
            totalAmount = totalAmount.add(payment.getAmount());
        }
        return round(totalAmount, OUTPUT_AMOUNT_SCALE);
    }

    /**
     * Проверяет заполненность свойств заявки на кредит.
     *
     * @param application заявка на кредит.
     * @throws IllegalArgumentException в случае, когда параметры не заполнены.
     */
    private void checkCreditApplication(final CreditApplication application) {
//        if (application.getPaymentType() == null) {
//            throw new IllegalArgumentException("Credit application must have non-null PaymentType param.");
//        }
//        if (application.getDuration() == null) {
//            throw new IllegalArgumentException("Credit application must have non-null duration in months param.");
//        }
        if (application.getStartDate() == null) {
            throw new IllegalArgumentException("Credit application must have non-null start date param.");
        }
    }
}
