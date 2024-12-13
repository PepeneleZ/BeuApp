package com.beuapp;

import javax.swing.SwingUtilities;

import com.beuapp.view.main.DefaultFrame;



class Main {
    public static void main(String[] args) {

        // Initialize the frame using invokeLater to ensure it loads properly
        SwingUtilities.invokeLater(() -> {
            DefaultFrame frame = new DefaultFrame();
            frame.setVisible(true);
        });

    }
        
}