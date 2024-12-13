package com.beuapp.view.forms.timetable;

import java.awt.*;
import javax.swing.*;

public class Timetable extends JPanel{
    public Timetable(){
        this.setBackground(new Color(244, 244, 249));
        this.setPreferredSize(new Dimension(1200, 850));

        this.setLayout(new BorderLayout());

        JLabel label = new JLabel("Timetable Not Implemented Yet", SwingConstants.CENTER);
        label.setFont(new Font("Roboto Mono", Font.PLAIN, 36));
        label.setHorizontalAlignment(SwingConstants.CENTER); // Horizontal centering
        label.setVerticalAlignment(SwingConstants.CENTER); 
        this.add(label);
        this.setVisible(true);
    }
}
