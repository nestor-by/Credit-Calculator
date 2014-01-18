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
        switch (application.getPaymentType()) {
            case ANNUITY: {
                /*
                 * payments are equal in total
                 * p = (S * I / 12) / (1 - (1 + I / 12)^(-M))
                 */
                Double body = application.getAmount();
                double monthlyRate = creditOffer.getRate() / CalendarUtil.NUMBER_OF_MONTHS;
                Integer durationInMonths = application.getDurationInMonths();
                Double amount = (body * monthlyRate) / (1
                        - Math.pow(1 + monthlyRate, -durationInMonths));
                totalTemp = amount * durationInMonths;
                Date date = application.getStartDate();

                double base = body;
                for (int i = 0; i < durationInMonths; i++) {
                    Double interest = base * monthlyRate;
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
                // todo
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
