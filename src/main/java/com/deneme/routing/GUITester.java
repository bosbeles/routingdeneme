package com.deneme.routing;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class GUITester {


    public static void test(Supplier<JPanel> panelSupplier) {
        EventQueue.invokeLater(() -> createAndShowGUI(panelSupplier));
    }

    private static void createAndShowGUI(Supplier<JPanel> panelSupplier) {
        for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
            if(lookAndFeelInfo.getName().contains("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        JFrame frame = new JFrame();

        frame.getContentPane().add(panelSupplier.get());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
