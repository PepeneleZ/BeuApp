package com.beuapp.view.menubar;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class MenuBar extends JPanel{

    private MenuEvent event;

    public MenuEvent getEvent(){
        return event;
    }
    public void setEvent(MenuEvent event){
        this.event = event;
    }

    private final String[][] buttons = {
        {"Dashboard"},
        {"Timetable", "View","Edit","Statistics"},
        {"Email", "Inbox", "Compose"}
        
    };
    
    public MenuBar() {
        // Set up the menu bar panel
        this.setPreferredSize(new Dimension(240, 850));
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(0x002137));
        this.setVisible(true);

        // Initialize the buttons
        initButtons();
    }

    private void initButtons(){

        // Call addMenu for each button in the array
        for(int i = 0; i < buttons.length; i++){
            addMenu(buttons[i][0], i);
        }
    }

    // Retrieves the icon for the buttons in the array
    private ImageIcon getIcon(String name){

        String iconPath = "resources\\menuicons\\" + name + ".png";

        ImageIcon icon = new ImageIcon(iconPath);
    
        // Check if the icon is valid
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Icon not found: " + iconPath);
            return null; 
        }
    
        return icon;
    }

    // Add the buttons in the array to the menu
    private void addMenu(String menuName, int index){
        int length = buttons[index].length;

        MenuButton button = new MenuButton(menuName, index, length > 1);
        button.setIcon(getIcon(menuName));

        // Add event for button pressed
        button.addActionListener((ActionEvent e) -> {
            /* 
            * If the sub array of the button is not empty, a sub menu with the buttons in the sub array is created. The original button will now just open and close the sub menu.
            * If the sub array of the button is empty, adds an event to the button.
            */ 
            if(length > 1){
                if(!button.isSelected()){
                    button.setSelected(true);
                    addSubMenu(index, length, getComponentZOrder(button));
                } else{
                    hideSubMenu(index);
                    button.setSelected(false);
                }
            } else{
                if(event != null){
                    event.selected(index, 0);
                }
            }
        });

        this.add(button);

        this.revalidate(); 
        this.repaint(); 
    }

    // Add the submenu to buttons whose sub arrays are not empty
    private void addSubMenu(int index, int length, int indexZorder) {
        JPanel subMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        // Set up the sub menu
        subMenu.setOpaque(true);
        subMenu.setPreferredSize(new Dimension(240, 0));
        subMenu.setName("subemenu- " + index);
        subMenu.setBackground(new Color(0x002E4E));

        // Add all buttons in the sub array to the sub menu
        for (int i = 1; i < length; i++) {
            MenuButton subButton = new MenuButton(buttons[index][i], i, false);

            // Add event for button pressed
            subButton.addActionListener((ActionEvent e) -> {
                if(event != null){
                    event.selected(index, subButton.getIndex());
                }
            });

            // Calls the initSubMenu method of the MenuButton class
            subButton.initSubMenu(i, length);

            subMenu.add(subButton);
        }

        // Add sub menu under its respective button
        this.add(subMenu, indexZorder + 1);

        // Animation for opening a sub menu
        Timer submenuSlideOutTimer = new Timer(10, null);
        submenuSlideOutTimer.addActionListener((ActionEvent e) -> {
            Dimension size = subMenu.getPreferredSize();
            int targetHeight = (length - 1) * ((JButton) subMenu.getComponent(0)).getPreferredSize().height ;
            
            if (size.height < targetHeight) { 
                subMenu.setPreferredSize(new Dimension(subMenu.getWidth(), size.height + 10)); 
                subMenu.revalidate();
            } else {
                subMenu.setPreferredSize(new Dimension(subMenu.getWidth(), targetHeight));
                submenuSlideOutTimer.stop(); 
            }
        });
        submenuSlideOutTimer.start(); 

        this.revalidate();
        this.repaint();
    }

    // Hide the sub menu when button pressed a second time
    private void hideSubMenu(int index){

        for(Component com : getComponents()){

            if(com instanceof JPanel && com.getName() != null && com.getName().equals("subemenu- " + index)){
                com.setName(null);

                // Animation for closing a sub menu
                Timer submenuSlideInTimer = new Timer(10, null);
                submenuSlideInTimer.addActionListener((ActionEvent e) -> {
                    Dimension size = com.getPreferredSize();
                    if (size.height > 0) { 
                        com.setPreferredSize(new Dimension(size.width, size.height - 10));
                        com.revalidate();
                    } else {
                        com.setPreferredSize(new Dimension(size.width, 0));
                        submenuSlideInTimer.stop();
                        MenuBar.this.remove(com);
                    }

                    this.revalidate();
                    this.repaint();
                });
                submenuSlideInTimer.start();
            }
        }
    }


}
