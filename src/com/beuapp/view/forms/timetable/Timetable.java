package com.beuapp.view.forms.timetable;

import com.beuapp.view.forms.FormsHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.table.DefaultTableModel;



public class Timetable extends JPanel{

    private final FormsHeader header;

    public Timetable() {

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0x00406C));

        header = new FormsHeader("Timetable", "View");

        String[] columnNames = {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Object[][] data = {
            {"", "", ""},
            {"", "", ""},
            {"", "", ""},
            {"", "", ""},
            {"", "", ""},
            {"", "", ""},
            {"", "", ""},
            {"", "", ""},
            {"", "", ""},


        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        table.setRowHeight(50);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(createEmptyBorder());
        scrollPane.setBackground(new Color(0x00406C));
        scrollPane.getViewport().setBackground(new Color(0x00406C));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        buttonPanel.setBackground(null);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JButton addRowButton = new JButton("+");
        addRowButton.setBorder(null);
        addRowButton.setFocusPainted(false);
        addRowButton.setBackground(new Color(0x002E4E));
        addRowButton.setForeground(Color.white);
        addRowButton.setFont(new Font("Roboto Mono", Font.PLAIN, 15));
        addRowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton removeRowButton = new JButton("-");
        removeRowButton.setBorder(null);
        removeRowButton.setFocusPainted(false);
        removeRowButton.setBackground(new Color(0x002E4E));
        removeRowButton.setForeground(Color.white);
        removeRowButton.setFont(new Font("Roboto Mono", Font.PLAIN, 15));
        removeRowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(addRowButton);
        buttonPanel.add(removeRowButton);

        addRowButton.addActionListener((ActionEvent e) -> {
            int selectedRow = table.getSelectedRow();
            model.insertRow(selectedRow + 1, new Object[]{"", "", ""});
        });

        removeRowButton.addActionListener((ActionEvent e) -> {
            int selectedRow = table.getSelectedRow(); // Get the selected row index
            if (selectedRow != -1) { // Check if a row is selected
                model.removeRow(selectedRow);
            } else {
                model.removeRow(table.getRowCount() - 1);
            }
        });


        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);
        tablePanel.setBackground(new Color(0X00406C));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        this.add(header, BorderLayout.NORTH);
        this.add(tablePanel, BorderLayout.CENTER);

        this.setVisible(true);
    }


}
