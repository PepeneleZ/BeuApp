package com.beuapp.view.main;

import com.beuapp.view.forms.timetable.Timetable;
import com.beuapp.view.menubar.MenuBar;
import com.beuapp.view.menubar.TestForm;
import java.awt.*;
import javax.swing.*;

public class DefaultFrame extends JFrame{

    private final JPanel header;
    private final JLabel headerText;
    private final MenuBar menuBar = new MenuBar();
    private final Body body = new Body();

    public DefaultFrame() {
        // Set up the frame
        this.setTitle("App Test");
        this.setSize(1440, 900);
        this.setResizable(true);
        this.getContentPane().setBackground(new Color(244, 244, 249));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        

        // Sets up the frame icon
        ImageIcon icon = new ImageIcon("resources\\appicons\\appicon.jpg");
        this.setIconImage(icon.getImage());

        // Sets up the header
        header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(47, 69, 80));
        header.setPreferredSize(new Dimension(100, 50));

        // Sets up the header text
        ImageIcon headerIcon = new ImageIcon("resources\\appicons\\header.png");
        headerText  = new JLabel("The Voices", headerIcon, JLabel.CENTER);
        headerText.setIconTextGap(10);
        headerText.setFont(new Font("Roboto Mono", Font.PLAIN, 36));
        headerText.setForeground(Color.white);
        header.add(headerText);

        // Adds the header to the frame
        this.add(header, BorderLayout.NORTH);

        // Set event for button pressed in menu bar
        menuBar.setEvent((int index, int subIndex) -> {
            if(index == 1 && subIndex == 1){
                showForm(new Timetable());
            } else{
                showForm(new TestForm("Form: " + index + " " + subIndex + " " + "Not Implemented Yet"));
            }
        });

        // Adds the menu bar to the frame
        menuBar.setPreferredSize(new Dimension(240, 0));
        this.add(menuBar, BorderLayout.WEST);

        // Adds the body to the frame
        body.setLayout(new BorderLayout());
        this.add(body, BorderLayout.CENTER);

        // Updates the frame
        this.revalidate();
        this.repaint();

    }

    private void showForm(Component com){
        body.removeAll();
        body.add(com);
        body.revalidate();
        body.repaint();
    }
}
