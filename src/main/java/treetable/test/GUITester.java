package treetable.test;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class GUITester {

    public static JFrame create(JComponent panel) {


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        if(panel != null) {
            frame.getContentPane().add(panel, BorderLayout.CENTER);
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
}
