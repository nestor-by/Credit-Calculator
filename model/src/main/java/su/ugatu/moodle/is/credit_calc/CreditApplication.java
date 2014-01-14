package su.ugatu.moodle.is.credit_calc;

import java.util.Currency;
import java.util.Date;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 20:39
 */
public interface CreditApplication {
    Double getAmount();
    Currency getCurrency();
    CreditPaymentType getPaymentType();
    Integer getDurationInMonths();
    Date getStartDate();

    CreditApplication setCurrency(final Currency currency);
    CreditApplication setPaymentType(final CreditPaymentType paymentType);
    CreditApplication setDurationInMonths(final Integer durationInMonths);
    CreditApplication setStartDate(final Date startDate);
}
