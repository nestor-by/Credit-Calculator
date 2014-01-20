package su.ugatu.moodle.is.credit_calc;

import su.ugatu.moodle.is.util.CalendarUtil;
import su.ugatu.moodle.is.util.FinUtil;

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

    private final Double creditAmount;
    private final Double effectiveRate;
    private final Double totalPayment;
    private final List<CreditPayment> payments;
    private final Double initialCreditCommission;

    CreditProposalImpl(final CreditApplication application,
                       final CreditOffer creditOffer) {

        if (application.getPaymentType() == null) {
            throw new IllegalArgumentException(
                    "Credit application must have non-null PaymentType");
        }
        this.payments = new ArrayList<CreditPayment>();
        this.creditAmount = application.getAmount();
        double totalTemp = 0;
        final int durationInMonths = application.getDurationInMonths();
        if (application.getStartDate() == null) {
            application.setStartDate(new Date());
        }
        Date date = application.getStartDate();
        Double rate = creditOffer.getRate();
        double monthlyRate = rate / CalendarUtil.NUMBER_OF_MONTHS;

        // zero payment is once commission
        double initialCommissionPayments = 0;
        Double onceCommAmount = creditOffer.getOnceCommissionAmount();
        if (onceCommAmount != null) {
            initialCommissionPayments += onceCommAmount;
        }
        Double onceCommissionPercent = creditOffer.getOnceCommissionPercent();
        if (onceCommissionPercent != null) {
            initialCommissionPayments += onceCommissionPercent * creditAmount;
        }

//        CreditPayment initialCreditCommission
//                = new CreditPaymentImpl(0d, date);
//        initialCreditCommission.setDebt(0d);
//        initialCreditCommission.setInterest(0d);
//        initialCreditCommission.setTotalLeft(creditAmount);
//        initialCreditCommission.setCommission(initialCommissionPayments);
        this.initialCreditCommission = initialCommissionPayments;

        Double monthlyCommAmount = creditOffer.getMonthlyCommissionAmount();
        Double monthlyCommPercent = creditOffer.getMonthlyCommissionPercent();

        double monthlyCommission = 0;
        if (monthlyCommAmount != null) {
            monthlyCommission += monthlyCommAmount;
        }
        if (monthlyCommPercent != null) {
            monthlyCommission += monthlyCommPercent * creditAmount;
        }

        switch (application.getPaymentType()) {
            case ANNUITY: {
                /*
                 * payments are equal in total
                 * p = (S * I / 12) / (1 - (1 + I / 12)^(-M))
                 */
                double amount = (creditAmount * monthlyRate) / (1
                        - Math.pow(1 + monthlyRate, -durationInMonths));

                double withCommAmount = amount;
                if (monthlyCommAmount != null) {
                    withCommAmount += monthlyCommAmount;
                }
                if (monthlyCommPercent != null) {
                    withCommAmount += monthlyCommPercent * creditAmount;
                }

                totalTemp = withCommAmount * durationInMonths;

                double base = creditAmount;

                for (int i = 0; i < durationInMonths; i++) {
                    double interest = base * monthlyRate;
                    base += interest;

                    date = CalendarUtil.nextMonthDate(date);
                    CreditPayment payment = new CreditPaymentImpl(withCommAmount, date);
                    payment.setDebt(amount - interest).setInterest(interest);
                    base -= amount;
                    payment.setTotalLeft(base);
                    payment.setCommission(monthlyCommission);
                    payments.add(payment);
                }
                break;
            }
            case DIFFERENTIAL: {
                double base = creditAmount;
                for (int k = 1; k <= durationInMonths; k++) {
                    double interest = base * monthlyRate;
                    base += interest;

                    double amount = (1.0d / durationInMonths
                            + rate / CalendarUtil.NUMBER_OF_MONTHS)
                            * creditAmount
                            -  (k - 1) * ((rate / CalendarUtil.NUMBER_OF_MONTHS
                            * creditAmount) / durationInMonths);

                    double withCommAmount = amount;
                    if (monthlyCommAmount != null) {
                        withCommAmount += monthlyCommAmount;
                    }
                    if (monthlyCommPercent != null) {
                        withCommAmount += monthlyCommPercent * creditAmount;
                    }

                    date = CalendarUtil.nextMonthDate(date);
                    CreditPayment payment
                            = new CreditPaymentImpl(withCommAmount, date);
                    payment.setDebt(creditAmount / durationInMonths);
                    base -= amount;
                    payment.setInterest(interest);
                    payment.setTotalLeft(base);
                    payment.setCommission(monthlyCommission);
                    payments.add(payment);
                }
                break;
            }
        }
        this.totalPayment = totalTemp;
        this.effectiveRate = FinUtil.calcEffectiveRate(this);
    }

    @Override
    public List<CreditPayment> getPayments() {
        return payments;
    }

    @Override
    public Double getTotalPayment() {
        return totalPayment;
    }

    @Override
    public Double getCreditAmount() {
        return creditAmount;
    }

    @Override
    public Double getEffectiveRate() {
        return effectiveRate;
    }

    @Override
    public Double getInitialCreditCommission() {
        return initialCreditCommission;
    }
}
