package su.ugatu.moodle.is.credit_calc;


import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

import su.ugatu.moodle.is.credit_calc.component.MainFrame;
import su.ugatu.moodle.is.credit_calc.component.MyModel;
import su.ugatu.moodle.is.credit_calc.component.MyPanel;
import su.ugatu.moodle.is.credit_calc.component.MyTextField;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BuildingGUI {
    private JFrame frame;
    private JPanel panel, tb;
    private JTextField lostName, firstName, patronymic;
    private JMenu file, help;
    private JMenuItem exit, about;
    private JButton btn, printer;

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
        panel = new MyPanel("Данные",200,200);
        tb = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tb.setPreferredSize(new Dimension(760,240));
        //tb.setBorder(BorderFactory.createLineBorder(Color.black,2));

        MyModel model = new MyModel();
        final JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table); // Добавление таблицы в панель полосу прокруток
        scrollPane.setPreferredSize(new Dimension(750,200));

        // тестовая добавления данных в таблицу
        for(int i=0; i < 24; i++){
            Calendar date = new GregorianCalendar(2014,06,27);
            date.add(Calendar.MONTH,i); // Инкремент месяца

            int day = date.get(Calendar.DAY_OF_MONTH);
            int month = date.get(Calendar.MONTH);
            int year = date.get(Calendar.YEAR);
            if(month == 0) month = 12;

            String dt = day+"."+month+"."+year;
            String[] str = {""+(i+1),""+dt,""+15049.81 ,""+10424.81,""+4625.00,""+0.00,""+289575.19};
            model.addData(str);
        }

        // создания текстовых полей
        lostName = new MyTextField("Фамилия");
        firstName = new MyTextField("Имя");
        patronymic = new MyTextField("Отчество");

        // создание кнопки
        btn = new JButton("Рассчитать");
        printer = new JButton("Печать");

        // добавление в панель
        panel.add(lostName);
        panel.add(firstName);
        panel.add(patronymic);
        panel.add(btn);
        tb.add(scrollPane);
        tb.add(printer);

        // добавление в главное окно
        frame.add(panel);
        frame.add(tb);

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
