package su.ugatu.moodle.is.credit_calc.action;


import su.ugatu.moodle.is.credit_calc.*;
import su.ugatu.moodle.is.credit_calc.component.MyModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Calc implements ActionListener{
    private JFrame component;
    private JTextField amount, durationInMonths, interestRate;
    private JComboBox paymentType;
    private JTable table;
    private JLabel label;

    public Calc(JFrame component, JTextField amount, JTextField durationInMonths, JTextField interestRate, JComboBox paymentType, JTable table, JLabel label){
        this.amount = amount;
        this.durationInMonths = durationInMonths;
        this.interestRate = interestRate;
        this.component = component;
        this.paymentType = paymentType;
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

            CreditApplication app = new CreditApplicationImpl(Double.valueOf(this.amount.getText()));
            app.setDurationInMonths(Integer.valueOf(this.durationInMonths.getText()));

            if(paymentType.getSelectedIndex() == 1)
                app.setPaymentType(CreditPaymentType.DIFFERENTIAL);
            else
                app.setPaymentType(CreditPaymentType.ANNUITY);

            CreditProposal proposal = new CreditProposalImpl(app, Double.valueOf(this.interestRate.getText()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            List<CreditPayment> payments = proposal.getPayments();
            label.setText("<html>Всего: "+decimalFormat.format(proposal.getTotalPayment())+" денежных единиц<br>"
                         +"Эффективная процентная ставка: "+(decimalFormat.format(proposal.getEffectiveRate() * 100)) + "%</html>");
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
                        "" + 0.00,
                        "" + decimalFormat.format(payment.getTotalLeft())};
                model.addData(str);
            }
        }
        else{
            JOptionPane.showMessageDialog(component, "Введите число");
        }
    }
}
