package com.deneme.matrix;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Enumeration;
import java.util.Random;

public class Matrix {

    private static JFrame frame;
    private static Random random = new Random();

    public static void main(String[] args) {
        EventQueue.invokeLater(Matrix::createAndShowGUI);
    }

    private static void createAndShowGUI() {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // not worth my time
            }
        }


        frame = new JFrame();


        frame.getContentPane().add(creatTable());

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }


    public static JTable creatTable() {
        Boolean[][] matrix = new Boolean[5][5];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = random.nextBoolean();
            }
        }

        MatrixTableModel tableModel = new MatrixTableModel(matrix, new String[]{"HOST", "1", "2", "3", "4"});
        JTable table = new JTable(tableModel);
        table.setRowSelectionAllowed(false);
        table.setShowGrid(true);
        table.setRowHeight(30);
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        while(columns.hasMoreElements()) {
            columns.nextElement().setPreferredWidth(30);
        }

        table.setDefaultRenderer(Boolean.class, new MatrixCellRenderer());



        return table;
    }
}
