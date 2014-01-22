package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 14.01.14
 * Time: 20:58
 */
public interface CreditPayment {
    Date getDate();

    BigDecimal getAmount();

    BigDecimal getDebt();

    BigDecimal getInterest();

    BigDecimal getTotalLeft();

    BigDecimal getCommission();

    CreditPayment setDebt(BigDecimal debt);

    CreditPayment setInterest(BigDecimal interest);

    CreditPayment setTotalLeft(BigDecimal totalLeft);

    CreditPayment setCommission(BigDecimal commission);
}
