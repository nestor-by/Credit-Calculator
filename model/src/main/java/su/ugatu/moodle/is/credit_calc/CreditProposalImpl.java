package su.ugatu.moodle.is.credit_calc;

import su.ugatu.moodle.is.util.CalendarUtil;

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

    private Double totalAmount;
    private List<CreditPayment> payments;
    private static final int NUMBER_OF_MONTHS = 12;

    public CreditProposalImpl(final CreditApplication application,
                              final CreditOffer creditOffer) {
        if (application.getPaymentType() == null) {
            throw new IllegalArgumentException(
                    "Credit application must have non-null PaymentType");
        }
        this.payments = new ArrayList<CreditPayment>();
        switch (application.getPaymentType()) {
            case ANNUITY: {
                /*
                 * payments are equal in total
                 * p = (S * I / 12) / (1 - (1 + I / 12)^(-M))
                 */
                Double body = application.getAmount();
                double monthlyRate = creditOffer.getRate() / NUMBER_OF_MONTHS;
                Integer durationInMonths = application.getDurationInMonths();
                Double amount = (body * monthlyRate) / (1
                        - Math.pow(1 + monthlyRate, -durationInMonths));
                this.totalAmount = amount * durationInMonths;
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
                //todo: effective rate
                break;
            }
            case DIFFERENTIAL: {
                // todo
                break;
            }
        }
    }

    @Override
    public List<CreditPayment> getPayments() {
        return payments;
    }

    @Override
    public Double getTotalAmount() {
        return totalAmount;
    }
}
