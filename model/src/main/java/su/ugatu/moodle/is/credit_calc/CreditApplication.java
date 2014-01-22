package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 20:39
 */
public interface CreditApplication {
    BigDecimal getAmount();

    String getCurrency();

    CreditPaymentType getPaymentType();

    Integer getDurationInMonths();

    Date getStartDate();

    CreditApplication setCurrency(final String currency);

    CreditApplication setPaymentType(final CreditPaymentType paymentType);

    CreditApplication setDurationInMonths(final Integer durationInMonths);

    CreditApplication setStartDate(final Date startDate);
}
