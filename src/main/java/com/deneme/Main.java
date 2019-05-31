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

        int n = 10;

        String[] links = new String[n+1];
        links[0] = "HOST";
        int prefix = 1000;
        for (int i = 0; i < n; i++) {
            links[i+1] = (prefix + i) + "";
        }

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        ImageIcon ıcon = new ImageIcon(Main.class.getResource("/Filter-02.png"));
        Image image = ıcon.getImage(); // transform it
        // scale it the smooth way
        newimg = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);


        ClickablePanel[][] clickables = new ClickablePanel[links.length + 2][links.length + 2];

        for (int i = 0; i < links.length + 2; i++) {
            for (int j = 0; j < links.length + 2; j++) {
                gc.gridx = j;
                gc.gridy = i;

                final int x = i;
                final int y = j;
                Runnable r = () -> {
                    if (y == 0 || x == 0) {
                        for (int k = 2; k < links.length + 2; k++) {
                            for (int m = 2; m < links.length + 2; m++) {

                                ClickablePanel clickablePanel = clickables[k][m];
                                if (clickablePanel != null) {
                                    boolean selected = clickables[k][0].checkBox.isSelected() && clickables[0][m].checkBox.isSelected();
                                    clickablePanel.setEnabled(selected);
                                }
                            }


                        }


                    }

                };


                if (i == j) {

                    if(i == 1) {
                        JPanel p = new JPanel() {
                            @Override
                            public void paint(Graphics g) {
                                super.paint(g);
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

                                g2d.drawString("To", 30, 15);
                                g2d.drawString("From", 0, this.getHeight() );
                                g2d.setStroke(new BasicStroke(2f));
                                g2d.drawLine(0,0, this.getWidth(), this.getHeight());

                            }
                        };
                        p.setPreferredSize(new Dimension(50, 30));
                        panel.add(p, gc);
                    }
                    else {
                        panel.add(new JLabel(), gc);

                    }
                } else if (i == 1) {

                    HeaderButton button = new HeaderButton(j > 1 ? links[j - 2] : "RX");
                    int col = j;
                    button.addActionListener(e -> {
                        int count = 0;
                        for (int k = 0; k < links.length + 2; k++) {
                            ClickablePanel p = clickables[k][col];
                            if (p != null && p.checkBox.isSelected()) {
                                count++;
                            }
                        }

                        if (count == links.length - 1) {
                            button.setSelectedAll(false);
                        } else if (count == 0) {
                            button.setSelectedAll(true);
                        } else {
                            button.setSelectedAll(!button.isSelectedAll());
                        }
                        for (int k = 0; k < links.length + 2; k++) {
                            ClickablePanel p = clickables[k][col];
                            if (p != null) {
                                p.setSelected(button.isSelectedAll());
                            }
                        }
                        r.run();
                    });
                    panel.add(button, gc);
                } else {
                    if (j == 1) {
                        HeaderButton button = new HeaderButton(i > 1 ? links[i - 2] : "TX");
                        int row = i;

                        button.addActionListener(e -> {
                            int count = 0;
                            for (int k = 0; k < links.length + 2; k++) {
                                ClickablePanel p = clickables[row][k];
                                if (p != null && p.checkBox.isSelected()) {
                                    count++;
                                }
                            }
                            if (count == links.length - 1) {
                                button.setSelectedAll(false);
                            } else if (count == 0) {
                                button.setSelectedAll(true);
                            } else {
                                button.setSelectedAll(!button.isSelectedAll());
                            }
                            for (int k = 0; k < links.length + 2; k++) {
                                ClickablePanel p = clickables[row][k];
                                if (p != null) {
                                    p.setSelected(button.isSelectedAll());
                                }
                            }
                            r.run();
                        });
                        panel.add(button, gc);
                    } else {

                        clickables[i][j] = clickablePanel(r);
                        panel.add(clickables[i][j], gc);
                    }
                }


            }
        }

        for (int k = 2; k < links.length + 2; k++) {
            for (int m = 2; m < links.length + 2; m++) {

                ClickablePanel clickablePanel = clickables[k][m];
                if (clickablePanel != null) {
                    boolean selected = clickables[k][0].checkBox.isSelected() && clickables[0][m].checkBox.isSelected();
                    clickablePanel.setEnabled(selected);
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


    static ClickablePanel clickablePanel(Runnable r) {


        ClickablePanel panel = new ClickablePanel(r);


        return panel;
    }

    static class ClickablePanel extends JPanel {

        private final JCheckBox checkBox;
        private final JButton button;

        public ClickablePanel(Runnable r) {
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
                if (r != null) {
                    r.run();
                }

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

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            button.setEnabled(enabled);
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
