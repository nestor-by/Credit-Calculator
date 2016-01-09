package by.nestor.credit;

import by.nestor.credit.payments.CreditPaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Заявка на получение кредита.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 20:39
 */
public interface CreditApplication {

    /**
     * @return размер запрошенного кредита в валюте {@link #getCurrency()}
     */
    BigDecimal getAmount();

    /**
     * @return строка валюты заявки на кредит в формате ISO 4217.
     */
    String getCurrency();

    /**
     * Устанавить валюту заявки на кредит.
     * @param currency код валюты по ISO 4217.
     * @return заявка на кредит, в целях fluent interface.
     */
    CreditApplication setCurrency(final String currency);

    /**
     * @return тип отплаты кредита (например, аннуитет или дифференцированный)
     */
    CreditPaymentType getPaymentType();

    /**
     * Установить тип оплаты кредита (нопример, аннуитет или дифф.).
     *
     * @param paymentType тип платежа.
     * @return заявка на кредит.
     */
    CreditApplication setPaymentType(final CreditPaymentType paymentType);

    /**
     * @return срок кредита в месяцах.
     */
    Integer getDuration();

    /**
     * Установить срок выплаты кредита в месяцах.
     * @param durationInMonths количество месяцев.
     * @return заявка на кредит, в целях fluent interface.
     */
    CreditApplication setDuration(final Integer durationInMonths);

    /**
     * @return день, в который планируется получить кредит.
     */
    LocalDate getStartDate();

    /**
     * Установить желаемую дату получения кредита.
     * @param startDate желаемая дата получения кредита.
     * @return заявка на кредит, в целях fluent interface.
     */
    CreditApplication setStartDate(final LocalDate startDate);
}
