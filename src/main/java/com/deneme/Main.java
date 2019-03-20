package com.deneme;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Main {


    private static ImageIcon mark;
    private static ImageIcon cross;

    static Color green = new Color(152, 251, 152);
    static Color red = new Color(199, 81, 80);

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> createAndShowGUI());
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

        mark = new ImageIcon(Main.class.getResource("/check_24.png"));
        cross = new ImageIcon(Main.class.getResource("/cross-24.png"));

        JFrame frame = new JFrame();



        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel routingTab = new JPanel();
        JPanel filterTab = new JPanel(new BorderLayout());

        tabbedPane.addTab("Routing Management", routingTab);
        tabbedPane.addTab("Routing Filters", filterTab);

        routingTab.add(createRouting());
        filterTab.add(createFilter(), BorderLayout.CENTER);

        frame.getContentPane().add(tabbedPane);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


    }

    private static JPanel createRouting() {


        String[] links = {"HOST", "1003", "1005", "1006"};

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        for (int i = 0; i < links.length + 1; i++) {
            for (int j = 0; j < links.length + 1; j++) {
                gc.gridx = j;
                gc.gridy = i;
                if (i == j) {
                    panel.add(new JLabel(), gc);
                } else if (i == 0) {
                    panel.add(new JButton(links[j - 1]), gc);
                } else {
                    if (j == 0) {
                        panel.add(new JButton(links[i - 1]), gc);
                    } else {
                        panel.add(clickablePanel(), gc);
                    }
                }


            }
        }

        JPanel legend = new JPanel();
        legend.add(createLegendButton(green));
        legend.add(new JLabel("No Filter"));

        legend.add(createLegendButton(red));
        legend.add(new JLabel("Filtered"));


        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = links.length+ 1;
        panel.add(legend , gc);


        return panel;
    }

    private static JComponent createLegendButton(Color color) {
        JPanel button = new JPanel();
        button.setPreferredSize(new Dimension(12,12));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setBackground(color);

        return button;
    }

    private static JPanel createFilter() {

        JPanel panel = new JPanel(new MigLayout("debug, fill"));

        JTable table = new JTable(new Object[][]{
                {"HOST", "1003", "Simulation", "12:24:23"},
                {"HOST", "1004", "Category Idendity", "12:23:23"}
        }, new Object[]{"From", "To", "Filter", "Time"});
        table.setRowHeight(30);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        JScrollPane pane = new JScrollPane(table);

        JButton delete = new JButton("Delete");
        JButton details = new JButton("Details");

        JLabel fromLabel = new JLabel("From: ");
        JLabel toLabel = new JLabel("To: ");
        JLabel filterLabel = new JLabel("Filter");

        String[] links = {"HOST", "1001"};
        JComboBox<String> fromCombo = new JComboBox<String>(links);
        JComboBox<String> toCombo = new JComboBox<String>(links);
        JComboBox<String> filterCombo = new JComboBox<String>(new String[]{"Echo", "Category/Identity", "Label/Sublabel"});

        JButton filter = new JButton("Apply Filter");


        panel.add(pane, "push, grow, span");
        panel.add(delete, "growx 0");
        panel.add(details, "gapleft push, align right, span");
        panel.add(fromLabel);
        panel.add(fromCombo, "growx");
        panel.add(toLabel);
        panel.add(toCombo, "growx");
        panel.add(filterLabel);
        panel.add(filterCombo, "growx");
        panel.add(filter);

        return panel;
    }


    static Random random = new Random();


    static JComponent clickablePanel() {
        JButton button = new JButton(cross);
        JCheckBox checkBox = new JCheckBox();

        button.setSize(32, 32);

        button.addActionListener(e -> {
            checkBox.setSelected(!checkBox.isSelected());

            if (checkBox.isSelected()) {
                button.setIcon(mark);
            } else {
                button.setIcon(cross);
            }


        });



        button.setBackground(random.nextBoolean() ? green : red);

        JPanel panel = new JPanel(new GridLayout());
        // panel.setBorder(BorderFactory.createLineBorder(Color.RED));
        panel.add(button);

        return panel;
    }


}
