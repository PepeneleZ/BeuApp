package com.beuapp;

import com.beuapp.view.main.DefaultFrame;
import javax.swing.SwingUtilities;

class Main {
    public static void main(String[] args) {

        // Initialize the frame using invokeLater to ensure it loads properly
        SwingUtilities.invokeLater(() -> {
            DefaultFrame frame = new DefaultFrame();
            frame.setVisible(true);
        });

    }
        
}