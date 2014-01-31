package su.ugatu.moodle.is.credit_calc;

import su.ugatu.moodle.is.util.CalendarUtil;
import su.ugatu.moodle.is.util.Constants;
import su.ugatu.moodle.is.util.FinUtil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Реализация кредитного предложения.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
class CreditProposalImpl implements CreditProposal {

    private final BigDecimal creditAmount;      // размер кредита
    private final BigDecimal effectiveRate;     // эффективная процентная ставка
    private final BigDecimal totalPayment;      // полная стоимость кредита
    private final List<CreditPayment> payments; // список платежей
    private final BigDecimal initCredComm;      // первоначальная комиссия

    CreditProposalImpl(final CreditApplication application,
                       final CreditOffer creditOffer) {

        if (application.getPaymentType() == null) {
            throw new IllegalArgumentException(
                    "Credit application must have non-null PaymentType");
        }
        this.payments = new ArrayList<CreditPayment>();
        this.creditAmount = application.getAmount();

        BigDecimal zeroBD = new BigDecimal(0);
        BigDecimal oneBD = new BigDecimal(1);
        BigDecimal numMonthsBD = new BigDecimal(CalendarUtil.NUMBER_OF_MONTHS);

        BigDecimal totalTemp = zeroBD;
        final int durationInMonths = application.getDurationInMonths();
        if (application.getStartDate() == null) {
            application.setStartDate(new Date());
        }

        Date date = application.getStartDate();
        BigDecimal rate = creditOffer.getRate();
        BigDecimal monthlyRate = rate.divide(numMonthsBD,
                                Constants.CALC_SCALE, Constants.ROUNDING_MODE);

        // zero payment is once commission
        BigDecimal initCommPay = zeroBD.setScale(Constants.OUTPUT_AMOUNT_SCALE);
        BigDecimal onceCommAmount = creditOffer.getOnceCommissionAmount();
        if (onceCommAmount != null) {
            initCommPay = initCommPay.add(onceCommAmount);
        }
        BigDecimal onceCommPrcnt = creditOffer.getOnceCommissionPercent();
        if (onceCommPrcnt != null) {
            initCommPay = initCommPay.add(creditAmount.multiply(onceCommPrcnt));
        }

        this.initCredComm = initCommPay.setScale(Constants.OUTPUT_AMOUNT_SCALE);

        BigDecimal monCommAmount = creditOffer.getMonthlyCommissionAmount();
        BigDecimal monCommPrcnt = creditOffer.getMonthlyCommissionPercent();

        BigDecimal monComm = zeroBD.setScale(Constants.OUTPUT_AMOUNT_SCALE);
        if (monCommAmount != null) {
            monComm = monComm.add(monCommAmount);
        }
        if (monCommPrcnt != null) {
            monComm = monComm.add(creditAmount.multiply(monCommPrcnt));
        }

        BigDecimal durationInMonthsBD = new BigDecimal(durationInMonths);

        switch (application.getPaymentType()) {
            case ANNUITY: {
                /*
                 * payments are equal in total
                 * p = (S * I / 12) / (1 - (1 + I / 12)^(-M))
                 */
                BigDecimal denominator = oneBD;
                BigDecimal pow = monthlyRate.add(oneBD)
                        .pow(-durationInMonths, MathContext.DECIMAL64);
                denominator = denominator.subtract(pow);

                BigDecimal amount = creditAmount.multiply(monthlyRate)
                                        .divide(denominator,
                                                Constants.OUTPUT_AMOUNT_SCALE,
                                                Constants.ROUNDING_MODE);

                BigDecimal withCommAmount = amount;
                if (monCommAmount != null) {
                    withCommAmount = withCommAmount.add(monCommAmount);
                }
                if (monCommPrcnt != null) {
                    withCommAmount = withCommAmount
                            .add(creditAmount.multiply(monCommPrcnt));
                }

                totalTemp = withCommAmount.multiply(durationInMonthsBD);

                BigDecimal base = creditAmount;

                CreditPayment lastPayment = null;
                for (int i = 0; i < durationInMonths; i++) {
                    BigDecimal interest = base.multiply(monthlyRate);
                    base = base.add(interest);

                    date = CalendarUtil.nextMonthDate(date);
                    CreditPayment payment = new CreditPaymentImpl(
                                            withCommAmount.setScale(
                                                 Constants.OUTPUT_AMOUNT_SCALE),
                                            date);

                    payment.setDebt(amount.subtract(interest)
                            .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                                    Constants.ROUNDING_MODE));

                    payment.setInterest(interest
                            .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                                    Constants.ROUNDING_MODE));

                    base = base.subtract(amount);
                    payment.setTotalLeft(base.setScale
                            (Constants.OUTPUT_AMOUNT_SCALE,
                                    Constants.ROUNDING_MODE));

                    payment.setCommission(monComm
                                        .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                                                Constants.ROUNDING_MODE));
                    payments.add(payment);
                    lastPayment = payment;
                }
                // fixme костыль

                if (lastPayment != null) {
                    boolean lastPaymentLessThanOutputScale =
                            lastPayment.getTotalLeft().compareTo(
                              new BigDecimal(1
                                 / Math.pow(10, Constants.OUTPUT_AMOUNT_SCALE)))
                            < 0;
                    if (lastPaymentLessThanOutputScale) {
                        lastPayment.setTotalLeft(
                                zeroBD.setScale(Constants.OUTPUT_AMOUNT_SCALE));
                    }
                }
                break;
            }
            case DIFFERENTIAL: {
                BigDecimal base = creditAmount;
                for (int k = 1; k <= durationInMonths; k++) {
                    BigDecimal interest = base.multiply(monthlyRate);
                    base = base.add(interest);

                    BigDecimal firstSum = oneBD.divide(durationInMonthsBD,
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
                    if (monCommAmount != null) {
                        withCommAmount = withCommAmount.add(monCommAmount);
                    }
                    if (monCommPrcnt != null) {
                        withCommAmount = withCommAmount.add(
                                           creditAmount.multiply(monCommPrcnt));
                    }

                    totalTemp = totalTemp.add(withCommAmount);

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
                    payment.setCommission(monComm
                                        .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                                                  Constants.ROUNDING_MODE));
                    payments.add(payment);
                }
                break;
            }
        }
        this.totalPayment = totalTemp.setScale(Constants.OUTPUT_AMOUNT_SCALE,
                                               Constants.ROUNDING_MODE);
        this.effectiveRate = FinUtil.calcEffectiveRate(this);
    }

    @Override
    public List<CreditPayment> getPayments() {
        return payments;
    }

    @Override
    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    @Override
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    @Override
    public BigDecimal getEffectiveRate() {
        return effectiveRate;
    }

    @Override
    public BigDecimal getInitialCreditCommission() {
        return initCredComm;
    }
}
