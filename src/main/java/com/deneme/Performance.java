package com.deneme;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Performance {

    private static Set<Integer> myList;
    private static Map<Integer, Set<Integer>> directConnectionList;
    private static Map<Integer, Set<Integer>> stnList;

    public static void main(String[] args) {

        int noOfDirectList = 100;
        int noOfStnList = 100;
        int noOfIUConnected = 32;
        int noOfTestData = 10000;
        int upperBound = 30000;


        Random random = new Random();
        myList = new HashSet<>();
        for (int i = 0; i < noOfIUConnected; i++) {
            myList.add(random.nextInt(upperBound));
        }

        directConnectionList = new HashMap<>();
        for (int i = 0; i < noOfDirectList; i++) {
            Set<Integer> list = new HashSet<>();
            directConnectionList.put(i, list);
            for (int j = 0; j < noOfIUConnected; j++) {
                list.add(random.nextInt(upperBound));
            }
        }

        stnList = new HashMap<>();
        for (int i = 0; i < noOfStnList; i++) {
            Set<Integer> list = new HashSet<>();
            stnList.put(i, list);
            for (int j = 0; j < noOfIUConnected; j++) {
                list.add(random.nextInt(upperBound));
            }
        }

        int[] testData = new int[noOfTestData];
        for (int i = 0; i < testData.length; i++) {
            testData[i] = random.nextInt(upperBound);
        }


        long time = System.nanoTime();

        int successCount = 0;
        for (int data : testData) {
            int iu =  find(data);
            if(iu >= 0) {
                successCount++;
            }

        }

        time = System.nanoTime() - time;

        System.out.println("Success: " + successCount + " Time: " + TimeUnit.NANOSECONDS.toMillis(time));









    }

    private static int find(int i) {
        if(myList.contains(i)) {
            return 0;
        }

        Set<Map.Entry<Integer, Set<Integer>>> entries = directConnectionList.entrySet();
        for (Map.Entry<Integer, Set<Integer>> entry : entries) {
            if(entry.getValue().contains(i)) {
                return entry.getKey();
            }
        }


        entries = stnList.entrySet();
        for (Map.Entry<Integer, Set<Integer>> entry : entries) {
            if(entry.getValue().contains(i)) {
                return entry.getKey();
            }
        }

        return -1;
    }
}
