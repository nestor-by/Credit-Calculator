package su.ugatu.moodle.is.credit_calc.component;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MyPanel extends JPanel{
    public MyPanel(String nameGroup, int w, int h){
        super.setBorder(new CompoundBorder(new EmptyBorder(12,12,12,12),new TitledBorder(nameGroup)));
        super.setPreferredSize(new Dimension(w,h));

    }
}
