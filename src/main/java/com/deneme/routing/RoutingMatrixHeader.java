package com.deneme.routing;

import javax.swing.*;
import java.awt.*;

public class RoutingMatrixHeader extends JPanel {

    public RoutingMatrixHeader() {
        setPreferredSize(new Dimension(50, 30));
    }

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




}
