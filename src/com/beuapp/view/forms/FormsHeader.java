package com.beuapp.view.forms;

import java.awt.*;
import javax.swing.*;

public class FormsHeader extends JPanel{

    private final JLabel text;

    public FormsHeader(String title, String subtitle){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(new Color(0x002945));
        this.setPreferredSize(new Dimension(100, 40));

        text = new JLabel(title + " > " + subtitle, JLabel.CENTER);
        text.setFont(new Font("Roboto Mono", Font.PLAIN, 18));
        text.setForeground(Color.white);

        this.add(text);

        this.setVisible(true);
    }
}
