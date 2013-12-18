package su.ugatu.moodle.is.credit_calc;


import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

import su.ugatu.moodle.is.credit_calc.component.MainFrame;
import su.ugatu.moodle.is.credit_calc.component.MyPanel;
import su.ugatu.moodle.is.credit_calc.component.MyTextField;

import javax.swing.*;

import java.awt.event.ActionEvent;

public class BuildingGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField lostName, firstName, patronymic;
    private JMenu file, help;
    private JMenuItem exit, about;
    private JButton btn;

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
        panel = new MyPanel("Данные",200,200); // создания панели


        // создания текстовых полей
        lostName = new MyTextField("Фамилия");
        firstName = new MyTextField("Имя");
        patronymic = new MyTextField("Отчество");

        // создание кнопки
        btn = new JButton("Рассчитать");
        frame.add(panel);

        // добавление в панель текстовых полей
        panel.add(lostName);
        panel.add(firstName);
        panel.add(patronymic);
        // добавление в панель кнопку
        panel.add(btn);

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

        frame.repaint();
        frame.revalidate();

    }
}
