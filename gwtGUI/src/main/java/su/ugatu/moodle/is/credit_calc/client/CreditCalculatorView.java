package su.ugatu.moodle.is.credit_calc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import su.ugatu.moodle.is.credit_calc.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 19.01.14
 * Time: 1:43
 */
public class CreditCalculatorView extends Composite {

    interface CreditCalculatorViewUiBinder extends UiBinder<HTMLPanel, CreditCalculatorView> {
    }

    private static CreditCalculatorViewUiBinder ourUiBinder = GWT.create(CreditCalculatorViewUiBinder.class);

    @UiField
    HTMLPanel mainPanel;

    @UiField
    DoubleBox amount;

    @UiField
    DoubleBox rate;

    @UiField
    IntegerBox duration;

    @UiField
    ListBox paymentType;

    @UiField
    Button calc;

    Grid tableOfPayments;

    public CreditCalculatorView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("calc")
    void onCalcClick(ClickEvent event) {
        CreditApplication app = new CreditApplicationImpl(amount.getValue());
        app.setDurationInMonths(duration.getValue());
        if (paymentType.getSelectedIndex() == 0) {
            app.setPaymentType(CreditPaymentType.ANNUITY);
        } else if (paymentType.getSelectedIndex() == 1) {
            app.setPaymentType(CreditPaymentType.DIFFERENTIAL);
        }
        CreditOffer offer = new CreditOfferImpl().setRate(rate.getValue() / 100d);
        CreditProposal proposal = offer.calculateProposal(app);

        if (tableOfPayments != null) mainPanel.remove(tableOfPayments);

        tableOfPayments = new Grid(app.getDurationInMonths() + 1, 6);
        tableOfPayments.setText(0, 0, "№ платежа");
        tableOfPayments.setText(0, 1, "Дата платежа");
        tableOfPayments.setText(0, 2, "Сумма платежа");
        tableOfPayments.setText(0, 3, "Основной долг");
        tableOfPayments.setText(0, 4, "Начисленные проценты");
        tableOfPayments.setText(0, 5, "Остаток задолженности");
        List<CreditPayment> payments = proposal.getPayments();
        NumberFormat decFmt = NumberFormat.getFormat(".##");
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");
        for (int i = 1; i <= payments.size(); i++) {
            CreditPayment payment = payments.get(i - 1);
            tableOfPayments.setText(i, 0, String.valueOf(i));
            tableOfPayments.setText(i, 1, dateFormat.format(payment.getDate()));
            tableOfPayments.setText(i, 2, decFmt.format(payment.getAmount()));
            tableOfPayments.setText(i, 3, decFmt.format(payment.getDebt()));
            tableOfPayments.setText(i, 4, decFmt.format(payment.getInterest()));
            tableOfPayments.setText(i, 5, decFmt.format(payment.getTotalLeft()));
        }
        mainPanel.add(tableOfPayments);
    }
}