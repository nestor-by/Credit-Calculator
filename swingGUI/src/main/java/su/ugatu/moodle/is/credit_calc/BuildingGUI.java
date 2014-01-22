package su.ugatu.moodle.is.credit_calc;


import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

import su.ugatu.moodle.is.credit_calc.action.Calc;
import su.ugatu.moodle.is.credit_calc.component.MainFrame;
import su.ugatu.moodle.is.credit_calc.component.MyPanel;
import su.ugatu.moodle.is.credit_calc.component.MyTextField;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.text.MessageFormat;


public class BuildingGUI {
    private JFrame frame;
    private JPanel panel, tb, resPan;
    private JTextField amount, durationInMonths, interestRate, onceCommissionAmount,onceCommissionPercent, monthlyCommissionAmount, monthlyCommissionPercent;
    private JMenu file, help;
    private JMenuItem exit, about;
    private JButton btn, printer;
    private JComboBox paymentType;
    private JTable table;
    private JLabel label1, amountL, durationInMonthsL, interestRateL, onceCommissionAmountL,onceCommissionPercentL, monthlyCommissionAmountL, monthlyCommissionPercentL;
    private String[] str = {"Аннуитетная", "Дифференцированная"};

    public BuildingGUI(){
        ////////////////////  Подключение скина (библиотека JTattoo 1.6.10)  ///////////////////
        try {
            UIManager.setLookAndFeel(new AluminiumLookAndFeel()); // new McWinLookAndFeel(), AluminiumLookAndFeel(), new BernsteinLookAndFeel()
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //////////////////////////////////////////

        JMenuBar menuBar = new JMenuBar(); // создание панель меню
        //  создание мню
        file = new JMenu("Файл");
        help = new JMenu("Помощ");
        //  создание подменю
        exit = new JMenuItem("Выход");
        about = new JMenuItem("О программе");

        // добавление в панель меню
        menuBar.add(file);
        menuBar.add(help);
        file.add(exit);
        help.add(about);

        frame = new MainFrame(); // создания главного окна
        frame.setJMenuBar(menuBar); // добавление панель меню
        // создания панели
        panel = new MyPanel("Параметры кредита",300,400);
        tb = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tb.setPreferredSize(new Dimension(760,240));
        resPan = new JPanel();
        resPan.setPreferredSize(new Dimension(450,230));
        resPan.setBorder(BorderFactory.createLineBorder(Color.black,2));/////

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table); // Добавление таблицы в панель полосу прокруток
        scrollPane.setPreferredSize(new Dimension(750,200));

        // создания текстовых полей
        amount = new MyTextField("Например, «300000»");
        durationInMonths = new MyTextField("Например, «36»");
        interestRate = new MyTextField("Например, «23.2» (с точкой)");
        onceCommissionAmount = new MyTextField("Например, «3000»");
        onceCommissionPercent = new MyTextField("Например, «1.5» (с точкой)");
        monthlyCommissionAmount  =new MyTextField("Например, «100»");
        monthlyCommissionPercent = new MyTextField("Например, «0.05» (с точкой)");
        //Создания надпеси
        label1 = new JLabel("");
        amountL = new JLabel("Сумма кредита");
        durationInMonthsL = new JLabel("Срок кредита");
        interestRateL = new JLabel("Процентная ставка");
        onceCommissionAmountL = new JLabel("Разовая комиссия (в деньгах)");
        onceCommissionPercentL = new JLabel("Разовая комиссия (в процентах)");
        monthlyCommissionAmountL  =new JLabel("Ежемесячная комиссия (в деньгах)");
        monthlyCommissionPercentL = new JLabel("Ежемесячная комиссия (в процентах)");
        label1.setFont(new FontUIResource("Arial",2,20));

        // создание кнопки
        btn = new JButton("Рассчитать");
        printer = new JButton("Печать");

        // создания ComboBox
        paymentType = new JComboBox(str);

        // добавление в панель
        panel.add(amountL);
        panel.add(amount);
        panel.add(durationInMonthsL);
        panel.add(durationInMonths);
        panel.add(interestRateL);
        panel.add(interestRate);
        panel.add(onceCommissionAmountL);
        panel.add(onceCommissionAmount);
        panel.add(onceCommissionPercentL);
        panel.add(onceCommissionPercent);
        panel.add(monthlyCommissionAmountL);
        panel.add(monthlyCommissionAmount);
        panel.add(monthlyCommissionPercentL);
        panel.add(monthlyCommissionPercent);

        panel.add(paymentType);
        panel.add(btn);
        tb.add(scrollPane);
        tb.add(printer);
        resPan.add(label1, BorderLayout.CENTER);

        // добавление в главное окно
        frame.add(panel);
        frame.add(resPan);
        frame.add(tb);


        // слушатель кнопки (расчет)
        btn.addActionListener(new Calc(frame, amount, durationInMonths, interestRate, paymentType, table, label1,onceCommissionAmount,onceCommissionPercent, monthlyCommissionAmount, monthlyCommissionPercent));

        // слушатель выход
        exit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {System.exit(0);}});
        // слушатель о программе
        about.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"Кредитный калькулятор\nCopyright 2013-2014 UGATU\nВерсия 1.0","О программе",1);
            }
        });
        // слушатель печать
        printer.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    table.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat("График платежей"), new MessageFormat("- {0} -"));
                } catch (PrinterException pe){
                    System.err.println("Error printing: " + pe.getMessage());
                }
            }
        });

        frame.repaint();
        frame.revalidate();

    }
}
