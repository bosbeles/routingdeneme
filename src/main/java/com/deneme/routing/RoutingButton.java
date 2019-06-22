package com.deneme.routing;

import com.deneme.Main1;

import javax.swing.*;
import java.awt.*;

public class RoutingButton extends JPanel implements Selectable {

    private final Image filterImage;
    private boolean filtered;
    private boolean selected;


    private JButton button;

    public RoutingButton() {

        ImageIcon icon = new ImageIcon(getClass().getResource("/Filter-02.png"));
        // scale it the smooth way
        filterImage = icon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);

        initializePanel();
    }

    protected void initializePanel() {
        button = new JButton(" ") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (RoutingButton.this.isFiltered()) {
                    g.drawImage(filterImage, getSize().width - 12, 2, null);
                }


            }
        };
        add(button);
    }


    @Override
    public boolean isFiltered() {
        return filtered;
    }

    @Override
    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
        button.repaint();
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
