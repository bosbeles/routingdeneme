package com.deneme.routing;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        GUITester.test(Main::createTest);
    }

    private static JPanel createTest() {
        String[] links = {"HOST", "1001", "1002", "1003", "1004"};

        boolean[] txList = {true, true, true, true, true};
        boolean[] rxList = {true, true, true, true, true};

        boolean[][] routingMatrix = {{false, true, true, true, true}, {true, false, false, false, false}, {true, false, false, false, false}, {true, false, false, false, false}, {true, false, false, false, false}};
        RoutingMatrixPanel panel = new RoutingMatrixPanel(links, txList, rxList, routingMatrix);


        return panel;
    }


}
