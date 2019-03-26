package com.deneme;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {


    private static ImageIcon mark;
    private static ImageIcon cross;

    static Color green = new Color(152, 251, 152);
    static Color red = new Color(199, 81, 80);
    private static JFrame frame;
    private static Image newimg;

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

        frame = new JFrame();


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


        String[] links = {"HOST", "1003", "1005", "1006", "1007", "1008", "1009", "1010", "1011"};

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        ImageIcon ıcon = new ImageIcon(Main.class.getResource("/Filter-02.png"));
        Image image = ıcon.getImage(); // transform it
        // scale it the smooth way
        newimg = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);


        ClickablePanel[][] clickables = new ClickablePanel[links.length + 1][links.length + 1];

        for (int i = 0; i < links.length + 1; i++) {
            for (int j = 0; j < links.length + 1; j++) {
                gc.gridx = j;
                gc.gridy = i;
                if (i == j) {
                    panel.add(new JLabel(), gc);
                } else if (i == 0) {
                    HeaderButton button = new HeaderButton(links[j - 1]);
                    int col = j;
                    button.addActionListener(e -> {
                        int count = 0;
                        for (int k = 0; k < links.length + 1; k++) {
                            ClickablePanel p = clickables[k][col];
                            if (p != null && p.checkBox.isSelected()) {
                                count++;
                            }
                        }

                        if(count == links.length - 1) {
                            button.setSelectedAll(false);
                        }
                        else if(count == 0) {
                            button.setSelectedAll(true);
                        } else {
                            button.setSelectedAll(!button.isSelectedAll());
                        }
                        for (int k = 0; k < links.length + 1; k++) {
                            ClickablePanel p = clickables[k][col];
                            if (p != null) {
                                p.setSelected(button.isSelectedAll());
                            }
                        }
                    });
                    panel.add(button, gc);
                } else {
                    if (j == 0) {
                        HeaderButton button = new HeaderButton(links[i - 1]);
                        int row = i;

                        button.addActionListener(e -> {
                            int count = 0;
                            for (int k = 0; k < links.length + 1; k++) {
                                ClickablePanel p = clickables[row][k];
                                if (p != null && p.checkBox.isSelected()) {
                                    count++;
                                }
                            }
                            if(count == links.length - 1) {
                                button.setSelectedAll(false);
                            }
                            else if(count == 0) {
                                button.setSelectedAll(true);
                            } else {
                                button.setSelectedAll(!button.isSelectedAll());
                            }
                            for (int k = 0; k < links.length + 1; k++) {
                                ClickablePanel p = clickables[row][k];
                                if (p != null) {
                                    p.setSelected(button.isSelectedAll());
                                }
                            }
                        });
                        panel.add(button, gc);
                    } else {
                        clickables[i][j] = clickablePanel();
                        panel.add(clickables[i][j], gc);
                    }
                }


            }
        }

        JPanel legend = new JPanel();

        legend.add(createLegendButton(red));
        legend.add(new JLabel("Filtered"));


        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = links.length + 1;
        panel.add(legend, gc);


        return panel;
    }

    private static JComponent createLegendButton(Color color) {
        JPanel button = new JPanel();
        button.setPreferredSize(new Dimension(20, 20));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // button.setBackground(color);
        button.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        button.add(new JLabel(new ImageIcon(newimg), SwingConstants.LEADING));

        return button;
    }

    private static JPanel createFilter() {

        JPanel panel = new JPanel(new MigLayout("fill"));

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
        panel.add(fromLabel);

        panel.add(fromCombo, "growx");
        panel.add(toLabel);
        panel.add(toCombo, "growx");
        panel.add(filterLabel);
        panel.add(filterCombo, "growx");

        panel.add(filter);

        return panel;
    }


    static ClickablePanel clickablePanel() {


        ClickablePanel panel = new ClickablePanel();


        return panel;
    }

    static class ClickablePanel extends JPanel {

        private final JCheckBox checkBox;
        private final JButton button;

        public ClickablePanel() {
            boolean yes = ThreadLocalRandom.current().nextBoolean();

            button = new JButton(cross) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    if (yes) {
                        g.drawImage(newimg, getSize().width - 18, 2, null);
                    }

                }
            };
            checkBox = new JCheckBox();

            button.setSize(32, 32);


            button.addActionListener(e -> {
                setSelected(!checkBox.isSelected());
            });

            setSelected(ThreadLocalRandom.current().nextBoolean());


            setLayout(new GridLayout());
            // panel.setBorder(BorderFactory.createLineBorder(Color.RED));
            this.add(button);
        }

        public void setSelected(boolean selected) {
            checkBox.setSelected(selected);

            if (checkBox.isSelected()) {
                button.setIcon(mark);
                button.setBackground(green);
            } else {
                button.setIcon(cross);
                button.setBackground(red);
            }
        }
    }

    static class HeaderButton extends JButton {
        boolean selectedAll;
        public HeaderButton(String text) {
            super(text);
        }

        public boolean isSelectedAll() {
            return selectedAll;
        }

        public void setSelectedAll(boolean selectedAll) {
            this.selectedAll = selectedAll;
        }

    }


}
