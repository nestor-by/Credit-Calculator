package su.ugatu.moodle.is.credit_calc.component;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        super.setTitle("Кредитный калькулятор");
        super.setSize(500, 300);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        super.setResizable(false);
        super.setVisible(true);
    }
}
