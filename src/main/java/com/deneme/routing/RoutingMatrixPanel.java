package com.deneme.routing;

import javax.swing.*;
import java.awt.*;

public class RoutingMatrixPanel extends JPanel {

    private String[] links;
    private boolean[] txList;
    private boolean[] rxList;
    private boolean[][] routingMatrix;

    private LinkButton[] toLinks;
    private LinkButton[] fromLinks;

    private TxRoutingButton[] txButtons;
    private RxRoutingButton[] rxButtons;

    private RoutingButton[][] routingButtons;

    private LinkButton txButton;
    private LinkButton rxButton;

    public RoutingMatrixPanel(String[] links, boolean[] txList, boolean[] rxList, boolean[][] routingMatrix) {
        this.links = links;
        this.txList = txList;
        this.rxList = rxList;
        this.routingMatrix = routingMatrix;
        initializePanel();
    }

    private void initializePanel() {
        createButtons();
        addButtonsToPanel();

    }

    private void addButtonsToPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        // Header
        gc.gridx = 1;
        gc.gridy = 1;
        this.add(new RoutingMatrixHeader(), gc);





        // Tx Button
        int x = 2;
        int y = 2;

        for (int i = 0; i < txButtons.length; i++) {
            gc.gridx = x + i;
            gc.gridy = 0;
            this.add(txButtons[i], gc);
        }


        // to Links
        for (int i = 0; i < toLinks.length; i++) {
            gc.gridx = x + i;
            gc.gridy = 1;
            this.add(toLinks[i], gc);
        }

        // rx Button
        for (int i = 0; i < rxButtons.length; i++) {
            gc.gridx = 0;
            gc.gridy = y + i;
            this.add(rxButtons[i], gc);
        }

        // from Links

        for (int i = 0; i < fromLinks.length; i++) {
            gc.gridx = 1;
            gc.gridy = y + i;
            this.add(fromLinks[i], gc);
        }

        for (int i = 0; i < routingButtons.length; i++) {
            for (int j = 0; j < routingButtons.length; j++) {
                gc.gridx = x + j;
                gc.gridy = y + i;
                this.add(routingButtons[i][j], gc);
            }
        }


        // Rx Header
        gc.gridx = 0;
        gc.gridy = 1;
        this.add(rxButton, gc);

        // Tx Header
        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.EAST;
        this.add(txButton, gc);





    }

    private void createButtons() {
        toLinks = new LinkButton[links.length];
        for (int i = 0; i < toLinks.length; i++) {
            toLinks[i] = new LinkButton(links[i]);
        }

        fromLinks = new LinkButton[links.length];
        for (int i = 0; i < fromLinks.length; i++) {
            fromLinks[i] = new LinkButton(links[i]);
        }

        txButtons = new TxRoutingButton[links.length];
        for (int i = 0; i < txButtons.length; i++) {
            txButtons[i] = new TxRoutingButton();
        }

        rxButtons = new RxRoutingButton[links.length];
        for (int i = 0; i < rxButtons.length; i++) {
            rxButtons[i] = new RxRoutingButton();
            rxButtons[i].setFiltered(true);
        }

        routingButtons = new RoutingButton[links.length][links.length];
        for (int i = 0; i < routingButtons.length; i++) {
            for (int j = 0; j < routingButtons.length; j++) {
                routingButtons[i][j] = new RoutingButton();
            }
        }

        txButton = new LinkButton("Tx");

        rxButton = new LinkButton("Rx");
    }


}
