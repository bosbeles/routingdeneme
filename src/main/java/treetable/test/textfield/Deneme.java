package treetable.test.textfield;

import treetable.test.GUITester;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Deneme {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        //UIManager.setLookAndFeel(new NimbusLookAndFeel());
        JSearchTextField field = new JSearchTextField();
        field.setIcon(new ImageIcon(Deneme.class.getResource("/find-24x24.png")));
        GUITester.create(field);
        System.out.printf("asdas");
    }
}
