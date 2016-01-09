package by.nestor.credit;

import by.nestor.credit.payments.CreditPayment;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация кредитного предложения.
 *
 * @author rinat.enikeev@gmail.com
 *         Date: 13.01.14
 *         Time: 18:50
 */
class CreditProposalImpl implements CreditProposal {

    private final BigDecimal creditAmount;      // размер кредита
    private final BigDecimal effectiveRate;     // эффективная процентная ставка
    private final BigDecimal totalPayment;      // полная стоимость кредита
    private final List<CreditPayment> payments; // список платежей
    private final BigDecimal initCredComm;      // первоначальная комиссия
    private final BigDecimal totalCredComm;     // итоговая комиссия


    CreditProposalImpl(final BigDecimal creditAmount,
                       final BigDecimal effectiveRate,
                       final BigDecimal totalPayment,
                       final List<CreditPayment> payments,
                       final BigDecimal initCredComm,
                       final BigDecimal totalCredComm) {
        this.creditAmount = creditAmount;
        this.effectiveRate = effectiveRate;
        this.totalPayment = totalPayment;
        this.payments = payments;
        this.initCredComm = initCredComm;
        this.totalCredComm = totalCredComm;
    }

    @Override
    public List<CreditPayment> getPayments() {
        return payments;
    }

    @Override
    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    @Override
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    @Override
    public BigDecimal getEffectiveRate() {
        return effectiveRate;
    }

    @Override
    public BigDecimal getInitialCommission() {
        return initCredComm;
    }

    public BigDecimal getTotalCreditCommission() {
        return totalCredComm;
    }
}
