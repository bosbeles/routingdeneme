package com.deneme;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main1 {

    private static boolean redBackground = false;
    private static boolean crossIcon = false;
    private static boolean sameBehavior = false;


    static Color red = new Color(152, 251, 152);
    static Color green = new Color(43, 137, 199);

    static int N = 6;

    private static ImageIcon mark;
    private static ImageIcon cross;


    private static JFrame frame;
    private static Image newimg;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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

        mark = new ImageIcon(Main1.class.getResource("/check_24.png"));
        cross = new ImageIcon(Main1.class.getResource("/cross-24.png"));

        frame = new JFrame();


        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel routingTab = new JPanel(new BorderLayout());
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

    private static JComponent createRouting() {


        String[] links = new String[N + 1];
        links[0] = "HOST";
        int prefix = 1000;
        for (int i = 0; i < N; i++) {
            links[i + 1] = (prefix + i) + "";
        }

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(1, 1, 1, 1);

        ImageIcon ıcon = new ImageIcon(Main1.class.getResource("/Filter-02.png"));
        Image image = ıcon.getImage(); // transform it
        // scale it the smooth way
        mark = new ImageIcon(mark.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        cross = new ImageIcon(cross.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        newimg = image.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
        //cross = null;


        ClickablePanel[][] clickables = new ClickablePanel[links.length + 2][links.length + 2];

        for (int i = 0; i < links.length + 2; i++) {
            for (int j = 0; j < links.length + 2; j++) {
                gc.gridx = j;
                gc.gridy = i;

                final int x = i;
                final int y = j;
                Runnable r = () -> updateClickables(links, clickables);


                if (i == j) {

                    if (i == 1) {
                        JPanel p = new JPanel() {
                            @Override
                            public void paint(Graphics g) {
                                super.paint(g);
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

                                g2d.drawString("To", 30, 15);
                                g2d.drawString("From", 0, this.getHeight());
                                g2d.setStroke(new BasicStroke(2f));
                                g2d.drawLine(0, 0, this.getWidth(), this.getHeight());

                            }
                        };
                        p.setPreferredSize(new Dimension(50, 30));
                        panel.add(p, gc);
                    } else {
                        panel.add(new JLabel(), gc);

                    }
                } else if (i == 1) {

                    String newText = "<html>";
                    if (j > 1) {
                        String text = links[j - 2];
                        for (int z = 0; z < text.length(); z++) {
                            newText += text.charAt(z) + "<br>";
                        }
                        newText += "</html>";
                    }
                    //HeaderButton button = new HeaderButton(j > 1 ? links[j - 2] : "RX", true);

                    HeaderButton button = new HeaderButton(j > 1 ? j + "" : "RX");

                    /*if(j>1) {
                        button.setPreferredSize(new Dimension(32,64));
                    }*/

                    //button.setMargin(new Insets(0,-5,0,-5));
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
                    if (j == 0) {
                        gc.anchor = GridBagConstraints.SOUTH;
                    }


                    panel.add(button, gc);
                    gc.anchor = GridBagConstraints.CENTER;
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

                        if (i == 0) {
                            gc.anchor = GridBagConstraints.EAST;
                        }
                        panel.add(button, gc);
                        gc.anchor = GridBagConstraints.CENTER;
                    } else {

                        clickables[i][j] = clickablePanel(r, i, j);
                        panel.add(clickables[i][j], gc);
                    }
                }


            }
        }

        updateClickables(links, clickables);

        JPanel legend = new JPanel();

        legend.add(createLegendButton(red));
        legend.add(new JLabel("Filtered"));


        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = links.length + 1;
        panel.add(legend, gc);
        gc.gridy++;

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            for (int i = 0; i < clickables.length; i++) {
                for (int j = 0; j < clickables.length; j++) {
                    ClickablePanel p = clickables[i][j];
                    if (p != null) {

                        p.setSelected(i < 3 || j < 3);
                    }
                }
            }
            updateClickables(links, clickables);
        });
        panel.add(resetButton, gc);


        JScrollPane pane = new JScrollPane(panel);
        pane.setPreferredSize(new Dimension(400, 400));

        return pane;
    }

    private static void updateClickables(String[] links, ClickablePanel[][] clickables) {
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


    static ClickablePanel clickablePanel(Runnable r, int x, int y) {


        ClickablePanel panel = new ClickablePanel(r, x, y);


        return panel;
    }

    static class ClickablePanel extends JPanel {

        private final JCheckBox checkBox;
        private final JButton button;
        private final Color old;
        private final int i;
        private final int j;
        private final Color disabledColor = new Color(192, 192, 192, 100);

        public ClickablePanel(Runnable r, int i, int j) {
            boolean yes = ThreadLocalRandom.current().nextBoolean();
            this.i = i;
            this.j = j;
            button = new JButton(cross) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    if (yes) {
                        g.drawImage(newimg, getSize().width - 12, 2, null);
                    }

                }
            };

            button.setContentAreaFilled(false);
            button.setOpaque(true);
            button.setBorder(new LineBorder(Color.BLACK));


            old = button.getBackground();

            checkBox = new JCheckBox();
            if (i > 1 && j > 1) {

                button.setPreferredSize(new Dimension(32, 32));
            } else {
                button.setPreferredSize(new Dimension(32, 32));
            }

            //button.setSize(32, 32);


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


            colorize();


        }

        private void colorize() {
            if (checkBox.isSelected()) {
                button.setIcon(mark);
                button.setBackground(green);
            } else {
                ImageIcon icon = crossIcon ? cross : null;
                Color color = redBackground ? red : old;

                button.setIcon(!sameBehavior && (i == 0 || j == 0) ? cross : icon);
                button.setBackground(!sameBehavior && (i == 0 || j == 0) ? color : color);
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
            this(text, false);


        }

        public HeaderButton(String text, boolean vertical) {
            super(text);
            if (vertical) {
                this.setUI(new VerticalButtonUI(90));
            }


        }

        public boolean isSelectedAll() {
            return selectedAll;
        }

        public void setSelectedAll(boolean selectedAll) {
            this.selectedAll = selectedAll;
        }

    }


}
