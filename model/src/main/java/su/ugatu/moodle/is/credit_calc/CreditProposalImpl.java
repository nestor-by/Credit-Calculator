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
class CreditProposalImpl implements CreditProposal {

    private final Double creditAmount;
    private final Double effectiveRate;
    private final Double totalPayment;
    private final List<CreditPayment> payments;

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
        final Double rate = creditOffer.getRate();
        Date date = application.getStartDate();
        double monthlyRate = rate / CalendarUtil.NUMBER_OF_MONTHS;

        switch (application.getPaymentType()) {
            case ANNUITY: {
                /*
                 * payments are equal in total
                 * p = (S * I / 12) / (1 - (1 + I / 12)^(-M))
                 */


                double amount = (creditAmount * monthlyRate) / (1
                        - Math.pow(1 + monthlyRate, -durationInMonths));
                totalTemp = amount * durationInMonths;

                double base = creditAmount;

                for (int i = 0; i < durationInMonths; i++) {
                    double interest = base * monthlyRate;
                    base += interest;

                    date = CalendarUtil.nextMonthDate(date);
                    CreditPayment payment = new CreditPaymentImpl(amount, date);
                    payment.setDebt(amount - interest).setInterest(interest);
                    base -= amount;
                    payment.setTotalLeft(base);
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
                    date = CalendarUtil.nextMonthDate(date);
                    CreditPayment payment = new CreditPaymentImpl(amount, date);
                    payment.setDebt(creditAmount / durationInMonths);
                    base -= amount;
                    payment.setInterest(interest);
                    payment.setTotalLeft(base);

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
}
