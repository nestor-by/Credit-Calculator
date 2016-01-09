package by.nestor.credit;

import by.nestor.credit.commission.Commission;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Оффер на кредит.
 *
 * @author rinat.enikeev@gmail.com
 *         Date: 13.01.14
 *         Time: 21:47
 */
public interface CreditOffer {

    /**
     * @return имя заявки, например, кредит "Особый"
     */
    String getName();

    /**
     * @return минимальный - максимальный размер кредита.
     */
    Interval<BigDecimal> getAmount();

    /**
     * @return ставка кредита.
     */
    BigDecimal getRate();

    /**
     * Устанавливает ставку оффера по кредиту, в долях от единицы.
     *
     * @param rate ставка в долях от единицы (0.1699 соответствует 16.99%).
     * @return оффер, в целях fluent interface.
     */
    CreditOffer setRate(BigDecimal rate);

    /**
     * @return валюта кредита в ISO 4217.
     */
    String getCurrency();

    /**
     * @return минимальный - максимальный срок кредита в месяцах.
     */
    Interval<Integer> getDuration();

    /**
     * @return частота рассчета кредита.
     */
    Frequency getFrequency();

    CreditOffer setFrequency(Frequency frequency);

    /**
     * @return комиссии кредита.
     */
    Collection<Commission> getCommissions();

    CreditOffer setCommissions(Commission... commissions);

    /**
     * Подготавливает предложение по кредиту, заполняя неустановленный поля
     * заявки на значения по умолчанию и рассчитывая график и размер платежей.
     *
     * @param application заявка на кредит.
     * @return предложение по кредиту.
     */
    CreditProposal calculateProposal(CreditApplication application);

    /**
     * Устанавливает  комиссии.
     *
     * @param commissions комиссии.
     * @return оффер, в целях fluent interface.
     */
    CreditOffer setCommissions(List<Commission> commissions);
}
