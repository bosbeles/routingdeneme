package com.deneme.matrix;

import javax.swing.table.DefaultTableModel;

public class MatrixTableModel extends DefaultTableModel {


    public MatrixTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Boolean.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return row != column && row != 1 && column != 1;
    }
}
