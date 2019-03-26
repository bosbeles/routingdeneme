package com.deneme.component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

public class IP extends JPanel {

    private static JFrame frame;


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


        frame = new JFrame();


        JPanel panel = new JPanel();
        panel.add(new JLabel("Ip"));
        panel.add(new IP());


        JFormattedTextField.AbstractFormatter displayFormatter = new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                return text;
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value == null) {
                    return "";
                }
                return value.toString();
            }
        };
        JFormattedTextField.AbstractFormatter editorFormatter = new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                text = text.replace('_', '\0');
                return text;
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value == null) {
                    return "___.___.___.___";
                }
                String text = value.toString();
                String[] words = text.split(".");


                return text;
            }
        };


        JFormattedTextField.AbstractFormatterFactory factory = new DefaultFormatterFactory(displayFormatter, displayFormatter, editorFormatter);
        JFormattedTextField f = new JFormattedTextField(factory);
        f.setColumns(15);
        panel.add(f);


        frame.getContentPane().add(panel);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


    }


    private JTextField part1;
    private JTextField part2;
    private JTextField part3;
    private JTextField part4;

    private final Border border;
    private final Border border2;

    public IP() {
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));
        part1 = createField(true);
        part2 = createField(true);
        part3 = createField(true);
        part4 = createField(false);

        this.add(part1);
        this.add(new JLabel("."));
        this.add(part2);
        this.add(new JLabel("."));
        this.add(part3);
        this.add(new JLabel("."));
        this.add(part4);

        Border border = new LineBorder(Color.lightGray, 1);
        Border margin = new EmptyBorder(2, 2, 2, 2);
        this.border = new CompoundBorder(border, margin);

        Border border2 = new LineBorder(new Color(115, 164, 209), 2);
        Border margin2 = new EmptyBorder(1, 1, 1, 1);
        this.border2 = new CompoundBorder(border2, margin2);
        //this.border = new EtchedBorder();
        // this.border = new JTextField().getBorder();
        this.setBorder(this.border);

    }


    private JTextField createField(boolean requestFocus) {

        JTextField field = new JTextField(3);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setBorder(null);

        ((AbstractDocument) field.getDocument()).setDocumentFilter(new MyIntFilter(3, requestFocus));

        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getSource() != part4 && (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == '.')) {
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                    e.consume();
                } else if (e.getSource() != part1 && (e.getKeyCode() == KeyEvent.VK_LEFT || (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && field.getText().isEmpty()))) {
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
                    e.consume();
                }
            }
        });

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field.selectAll();

                IP.this.setBorder(IP.this.border2);
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = field.getText();
                if (!text.isEmpty()) {
                    field.setText(String.valueOf(Integer.parseInt(text)));
                }
                IP.this.setBorder(IP.this.border);
            }
        });

        return field;
    }

    class MyIntFilter extends DocumentFilter {


        private final boolean requestFocus;
        private int maxLength = 0;

        public MyIntFilter(int maxLength, boolean requestFocus) {
            setMaxLength(maxLength);
            this.requestFocus = requestFocus;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void insertString(FilterBypass fb, int offset, String string,
                                 AttributeSet attr) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();

            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (maxLength > 0 && doc.getLength() + string.length() <= maxLength) {
                if (test(sb.toString())) {
                    super.insertString(fb, offset, string, attr);
                    if (sb.length() == maxLength) {
                        if (requestFocus) {
                            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                        }
                    }
                } else {
                    // warn the user and don't allow the insert
                }
            }
        }

        private boolean test(String text) {
            if (text.length() > maxLength) return false;
            if (text.isEmpty()) return true;
            try {
                int i = Integer.parseInt(text);
                return i >= 0 && i <= 255;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                            AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder(2);
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (test(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
                if (sb.length() == maxLength) {
                    if (requestFocus) {
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                    }

                }
            } else {
                // warn the user and don't allow the insert
            }

        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder(2);
            sb.append(doc.getText(0, doc.getLength()));
            //sb.append(doc.getText(0, 2));
            sb.delete(offset, offset + length);

            if (test(sb.toString())) {
                super.remove(fb, offset, length);
            } else {
                // warn the user and don't allow the insert
            }

        }
    }

}
