package com.deneme.routing;

import javax.swing.*;

public class LinkButton extends JPanel {

    private final String name;
    private JButton linkButton;


    public LinkButton(String name) {
        this.name = name;
        initializePanel();
    }

    protected void initializePanel() {
        this.linkButton = new JButton(name);
        this.add(linkButton);
    }
}
