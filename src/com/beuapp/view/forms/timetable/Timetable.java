package com.beuapp.view.forms.timetable;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Timetable extends JTable{

    public Timetable() {
        // Initialize the table with a default data model
        String[] columnNames = {"ID", "Name", "Age"};
        Object[][] data = {
            {1, "Alice", 25},
            {2, "Bob", 30},
            {3, "Charlie", 35}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        this.setModel(model);

        // Customize the table
        this.setRowHeight(30); // Set row height
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Single selection
        this.setAutoCreateRowSorter(true); // Enable sorting
    }

    // Custom behavior: Prevent editing of the first column
    @Override
    public boolean isCellEditable(int row, int column) {
        return column != 0; // Allow editing only for non-ID columns
    }

    // Add custom behavior as needed
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.setRowCount(0); // Clear all rows
    }
}
