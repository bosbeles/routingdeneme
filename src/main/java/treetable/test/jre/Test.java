package treetable.test.jre;

import org.jdesktop.swingx.JXTreeTable;
import treetable.test.*;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        JRETreeTableModel<Echo> model = new JRETreeTableModel<>(Arrays.asList("Data", "Response", "Sender", "Receiver", "Status"));


        JXTreeTable treeTable = new JXTreeTable(model);
        treeTable.setLeafIcon(null);

        /*treeTable.setOpenIcon(null);
        treeTable.setClosedIcon(null);
        */
        treeTable.setRowHeight((int) (treeTable.getRowHeight() * 1.4));
        JScrollPane     scrollpane = new JScrollPane(treeTable);


        JFrame frame = GUITester.create(scrollpane);

        JPanel panel = new JPanel();
        JTextField data = new JTextField(3);
        JTextField sender = new JTextField(3);
        JTextField receiver = new JTextField(3);
        JTextField status = new JTextField(12);
        JTextField response = new JTextField(3);

        JButton deneme = new JButton("DENEME");
        deneme.addActionListener(e-> {
            Echo echo = new Echo();
            echo.setSender(Integer.parseInt(sender.getText()));
            echo.setReceiver(Integer.parseInt(receiver.getText()));
            echo.setStatus(status.getText());
            echo.setResponse(Integer.parseInt(response.getText()));
            echo.setData(Integer.parseInt(data.getText()));
            model.add(echo);
            model.sort();
            treeTable.updateUI();
            treeTable.setLeafIcon(null);
             /*treeTable.setOpenIcon(null);
        treeTable.setClosedIcon(null);
        */



        });
        panel.add(data);
        panel.add(response);
        panel.add(sender);
        panel.add(receiver);
        panel.add(status);
        panel.add(deneme);
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);

    }
}
