package su.ugatu.moodle.is.credit_calc.component;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField implements FocusListener{
    private String name;
    public MyTextField(String name){
        this.name = name;
        setForeground(Color.GRAY);
        setFont(new FontUIResource(name,2,14));
        setText(this.name);
        addFocusListener(this); // добавление слушателя фокуса
        super.setPreferredSize(new Dimension(150,20));
    }

    // действие при сфокусированном текстовом поле
    @Override
     public void focusGained(FocusEvent e) {
        setForeground(Color.black);
        setFont(new FontUIResource(name,0,14));
        if(getText().equals(name)){
            setText("");
        }
    }

    // действие при расфокусированном текстовом поле
    @Override
    public void focusLost(FocusEvent e) {
        if(getText().equals("")){
            setForeground(Color.GRAY);
            setFont(new FontUIResource(name,2,14));
            setText(name);
        }
    }
}
