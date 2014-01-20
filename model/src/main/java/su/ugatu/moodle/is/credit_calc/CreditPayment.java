package su.ugatu.moodle.is.credit_calc;

import java.util.Date;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 14.01.14
 * Time: 20:58
 */
public interface CreditPayment {
    Date getDate();
    Double getAmount();

    Double getDebt();
    Double getInterest();
    Double getTotalLeft();
    Double getCommission();

    CreditPayment setDebt(Double debt);
    CreditPayment setInterest(Double interest);
    CreditPayment setTotalLeft(Double totalLeft);

    CreditPayment setCommission(Double commission);
}
