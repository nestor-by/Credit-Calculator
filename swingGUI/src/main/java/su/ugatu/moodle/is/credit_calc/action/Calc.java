package su.ugatu.moodle.is.credit_calc.action;


import su.ugatu.moodle.is.credit_calc.*;
import su.ugatu.moodle.is.credit_calc.component.MyModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class Calc implements ActionListener{
    private JFrame component;
    private JTextField amount, durationInMonths, interestRate, onceCommissionAmount,onceCommissionPercent, monthlyCommissionAmount, monthlyCommissionPercent;
    private JComboBox paymentType;
    private JTable table;
    private JLabel label;
    private CreditApplication application;
    private CreditOffer offer;

    public Calc(JFrame component, JTextField amount, JTextField durationInMonths, JTextField interestRate, JComboBox paymentType, JTable table, JLabel label, JTextField onceCommissionAmount, JTextField onceCommissionPercent, JTextField MonthlyCommissionAmount, JTextField MonthlyCommissionPercent){
        this.amount = amount;
        this.durationInMonths = durationInMonths;
        this.interestRate = interestRate;
        this.component = component;
        this.paymentType = paymentType;

        this.onceCommissionAmount = onceCommissionAmount;
        this.onceCommissionPercent = onceCommissionPercent;
        this.monthlyCommissionPercent = MonthlyCommissionPercent;
        this.monthlyCommissionAmount = MonthlyCommissionAmount;

        this.table = table;
        this.label = label;

    }

    // проверка на число
    private boolean control(JTextField tf){
        double d;
        try {
            d = Double.parseDouble(tf.getText().toString());
            if(d < 0) tf.setText(""+Math.abs(d));
            }
        catch (NumberFormatException e){
            return false;
            }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(control(this.amount) && control(this.durationInMonths) && control(this.interestRate)){

            application = new CreditApplicationImpl(new BigDecimal(this.amount.getText()));
            application.setDurationInMonths(Integer.valueOf(this.durationInMonths.getText()));

            offer = new CreditOfferImpl();
            offer.setRate(new BigDecimal(this.interestRate.getText()).divide(new BigDecimal(100)));
            if(control(this.onceCommissionAmount))
                offer.setOnceCommissionAmount(new BigDecimal(this.onceCommissionAmount.getText()));
            else
                this.onceCommissionAmount.setText("0");

            if(control(this.onceCommissionPercent))
                offer.setOnceCommissionPercent(new BigDecimal(this.onceCommissionPercent.getText()).divide(new BigDecimal(100)));
            else
                this.onceCommissionPercent.setText("0");

            if(control(this.monthlyCommissionPercent))
                offer.setMonthlyCommissionPercent(new BigDecimal(this.monthlyCommissionPercent.getText()).divide(new BigDecimal(100)));
            else
                this.monthlyCommissionPercent.setText("0");

            if(control(this.monthlyCommissionAmount))
                offer.setMonthlyCommissionAmount(new BigDecimal(this.monthlyCommissionAmount.getText()));
            else
                this.monthlyCommissionAmount.setText("0");

            if(paymentType.getSelectedIndex() == 1){
                application.setPaymentType(CreditPaymentType.DIFFERENTIAL);
                CreditProposal proposal = offer.calculateProposal(application);
                printProposal(proposal);
            }
            else{
                application.setPaymentType(CreditPaymentType.ANNUITY);
                CreditProposal proposal = offer.calculateProposal(application);
                printProposal(proposal);
            }

        }
        else{
            JOptionPane.showMessageDialog(component, "Введите число");
        }


    }
    private void printProposal(CreditProposal proposal){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<CreditPayment> payments = proposal.getPayments();
        label.setText("<html>Всего: "+decimalFormat.format(proposal.getTotalPayment())+" денежных единиц<br>"
                +"Эффективная процентная ставка: "+(decimalFormat.format(proposal.getEffectiveRate().doubleValue() * 100)) + "%<br>" +
                "Комиссия: " + decimalFormat.format(proposal.getInitialCreditCommission())+"</html>");
        String[] str;
        int i = 1;
        MyModel model = new MyModel();
        table.setModel(model);
        for (CreditPayment payment: payments) {
            str = new String[]{
                    "" + (i++),
                    "" + dateFormat.format(payment.getDate()),
                    "" + decimalFormat.format(payment.getAmount()),
                    "" + decimalFormat.format(payment.getDebt()),
                    "" + decimalFormat.format(payment.getInterest()),
                    "" + decimalFormat.format(payment.getCommission()),
                    "" + decimalFormat.format(payment.getTotalLeft())};
            model.addData(str);
        }
    }
}
