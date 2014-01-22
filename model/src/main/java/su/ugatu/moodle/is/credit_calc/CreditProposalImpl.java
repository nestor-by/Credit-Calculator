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
 * Immutable.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
public class CreditProposalImpl implements CreditProposal {

    private final BigDecimal creditAmount;
    private final BigDecimal effectiveRate;
    private final BigDecimal totalPayment;
    private final List<CreditPayment> payments;
    private final BigDecimal initialCreditCommission;

    CreditProposalImpl(final CreditApplication application,
                       final CreditOffer creditOffer) {

        if (application.getPaymentType() == null) {
            throw new IllegalArgumentException(
                    "Credit application must have non-null PaymentType");
        }
        this.payments = new ArrayList<CreditPayment>();
        this.creditAmount = application.getAmount();
        BigDecimal totalTemp = new BigDecimal(0);
        final int durationInMonths = application.getDurationInMonths();
        if (application.getStartDate() == null) {
            application.setStartDate(new Date());
        }
        Date date = application.getStartDate();
        BigDecimal rate = creditOffer.getRate();
        BigDecimal monthlyRate = rate.divide(new BigDecimal(CalendarUtil.NUMBER_OF_MONTHS), Constants.CALC_SCALE, Constants.ROUNDING_MODE);

        // zero payment is once commission
        BigDecimal initialCommissionPayments = new BigDecimal(0).setScale(Constants.OUTPUT_AMOUNT_SCALE);
        BigDecimal onceCommAmount = creditOffer.getOnceCommissionAmount();
        if (onceCommAmount != null) {
            initialCommissionPayments = initialCommissionPayments.add(onceCommAmount);
        }
        BigDecimal onceCommissionPercent = creditOffer.getOnceCommissionPercent();
        if (onceCommissionPercent != null) {
            initialCommissionPayments = initialCommissionPayments.add(creditAmount.multiply(onceCommissionPercent));
        }

        this.initialCreditCommission = initialCommissionPayments.setScale(Constants.OUTPUT_AMOUNT_SCALE);

        BigDecimal monthlyCommAmount = creditOffer.getMonthlyCommissionAmount();
        BigDecimal monthlyCommPercent = creditOffer.getMonthlyCommissionPercent();

        BigDecimal monthlyCommission = new BigDecimal(0).setScale(Constants.OUTPUT_AMOUNT_SCALE);
        if (monthlyCommAmount != null) {
            monthlyCommission = monthlyCommission.add(monthlyCommAmount);
        }
        if (monthlyCommPercent != null) {
            monthlyCommission = monthlyCommission.add(creditAmount.multiply(monthlyCommPercent));
        }

        BigDecimal durationInMonthsBD = new BigDecimal(durationInMonths);

        switch (application.getPaymentType()) {
            case ANNUITY: {
                /*
                 * payments are equal in total
                 * p = (S * I / 12) / (1 - (1 + I / 12)^(-M))
                 */
                BigDecimal denominator = new BigDecimal(1);
                BigDecimal pow = monthlyRate.add(new BigDecimal(1))
                        .pow(-durationInMonths, MathContext.DECIMAL64);
                denominator = denominator.subtract(pow);

                BigDecimal amount = creditAmount.multiply(monthlyRate).divide(denominator, 2, Constants.ROUNDING_MODE);

                BigDecimal withCommAmount = amount;
                if (monthlyCommAmount != null) {
                    withCommAmount = withCommAmount.add(monthlyCommAmount);
                }
                if (monthlyCommPercent != null) {
                    withCommAmount = withCommAmount.add(creditAmount.multiply(monthlyCommPercent));
                }

                totalTemp = withCommAmount.multiply(durationInMonthsBD);

                BigDecimal base = creditAmount;

                CreditPayment lastPayment = null;
                for (int i = 0; i < durationInMonths; i++) {
                    BigDecimal interest = base.multiply(monthlyRate);
                    base = base.add(interest);

                    date = CalendarUtil.nextMonthDate(date);
                    CreditPayment payment = new CreditPaymentImpl(withCommAmount.setScale(Constants.OUTPUT_AMOUNT_SCALE), date);
                    payment.setDebt(amount.subtract(interest).setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE)).setInterest(interest.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE));
                    base = base.subtract(amount);
                    payment.setTotalLeft(base.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE));
                    payment.setCommission(monthlyCommission.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE));
                    payments.add(payment);
                    lastPayment = payment;
                }
                // fixme костыль
                if (lastPayment != null && lastPayment.getTotalLeft().compareTo(new BigDecimal(1 / Math.pow(10, Constants.OUTPUT_AMOUNT_SCALE))) < 0) {
                    lastPayment.setTotalLeft(new BigDecimal(0).setScale(Constants.OUTPUT_AMOUNT_SCALE));
                }
                break;
            }
            case DIFFERENTIAL: {
                BigDecimal base = creditAmount;
                for (int k = 1; k <= durationInMonths; k++) {
                    BigDecimal interest = base.multiply(monthlyRate);
                    base = base.add(interest);

                    BigDecimal firstSummand = new BigDecimal(1).divide(durationInMonthsBD, Constants.CALC_SCALE, Constants.ROUNDING_MODE)
                            .add(monthlyRate).multiply(creditAmount);
                    BigDecimal secondSummand = creditAmount.multiply(monthlyRate)
                            .divide(durationInMonthsBD, Constants.CALC_SCALE, Constants.ROUNDING_MODE).multiply(new BigDecimal(k - 1));

                    BigDecimal amount = firstSummand.subtract(secondSummand);

                    BigDecimal withCommAmount = amount;
                    if (monthlyCommAmount != null) {
                        withCommAmount = withCommAmount.add(monthlyCommAmount);
                    }
                    if (monthlyCommPercent != null) {
                        withCommAmount = withCommAmount.add(creditAmount.multiply(monthlyCommPercent));
                    }

                    totalTemp = totalTemp.add(withCommAmount);

                    date = CalendarUtil.nextMonthDate(date);
                    CreditPayment payment
                            = new CreditPaymentImpl(withCommAmount.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE), date);
                    payment.setDebt(creditAmount.divide(durationInMonthsBD, Constants.CALC_SCALE, Constants.ROUNDING_MODE).setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE));
                    base = base.subtract(amount);
                    payment.setInterest(interest.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE));
                    payment.setTotalLeft(base.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE));
                    payment.setCommission(monthlyCommission.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE));
                    payments.add(payment);
                }
                break;
            }
        }
        this.totalPayment = totalTemp.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE);
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
        return initialCreditCommission;
    }
}
