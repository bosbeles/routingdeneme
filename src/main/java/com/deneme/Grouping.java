package com.deneme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grouping {

    public static void main(String[] args) {

        int total = 1600;
        int participantCount = 4;
        int grouping = 250;
        int wordCount = 2;

        test(total, participantCount, grouping, wordCount);


        for (int p = 1; p <= 10; p++) {
            int min = Integer.MAX_VALUE;
            int minG = 10;
            for (int g = 10; g < 500; g += 10) {
                int o = optimized(total, p, g, wordCount);
                System.out.println("P: " + p + " G: " + g + " o: " + o);
                if (min > o) {
                    minG = g;
                    min = o;
                }
            }
            System.out.println("------P: " + p + " G: " + minG + " min: " + min);
        }
    }

    private static int optimized(int total, int participantCount, int grouping, int wordCount) {
        int totalMessage = total / (12 * participantCount);
        int messageInterval = 1000 / totalMessage;

        List<Participant> participants = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < participantCount; i++) {
            Participant p = new Participant(i);
            participants.add(p);
            for (int j = 0; j < 1000; j += messageInterval) {
                int slot = j / grouping;
                p.add(slot);
                count++;
            }
        }
        return optimized(grouping, wordCount, participants);
    }

    private static void test(int total, int participantCount, int grouping, int wordCount) {
        int totalMessage = total / (12 * participantCount);
        int messageInterval = 1000 / totalMessage;

        List<Participant> participants = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < participantCount; i++) {
            Participant p = new Participant(i);
            participants.add(p);
            for (int j = 0; j < 1000; j += messageInterval) {
                int slot = j / grouping;
                p.add(slot);
                count++;
            }
        }
        System.out.println();
        System.out.println("Message Count: " + count);

        System.out.println("Not optimized: " + (7 + 16 + 9 + 18) * participantCount * totalMessage);
        System.out.println("Optimized: " + optimized(grouping, wordCount, participants));

        System.out.println("Half Optimized: " + halfOptimized(grouping, wordCount, participants));
    }

    private static int halfOptimized(int grouping, int wordCount, List<Participant> participants) {
        int[] slots = new int[1000 / grouping];
        for (Participant participant : participants) {
            for (int i = 0; i < slots.length; i++) {
                Integer slotCount = participant.messages.get(i);
                if (slotCount > 0) {
                    if (slots[i] == 0) {
                        slots[i] = 7 + 16;
                    }
                    slots[i] += (9 + 9 * wordCount) * slotCount;
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < slots.length; i++) {

            sum += slots[i] + (slots[i] / 1400) * 23;
        }
        return sum;
    }

    private static int optimized(int grouping, int wordCount, List<Participant> participants) {
        int[] slots = new int[1000 / grouping + 1];
        for (Participant participant : participants) {
            for (int i = 0; i < slots.length; i++) {
                Integer slotCount = participant.messages.get(i);
                if (slotCount != null) {
                    if (slots[i] == 0) {
                        slots[i] = 7 + 16;
                    }
                    int maxMessage = 29 / wordCount;
                    int slotR = slotCount / maxMessage;
                    slots[i] += (9 + 9 * wordCount * maxMessage) * slotR + (9 + 9 * wordCount * (slotCount % maxMessage));
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < slots.length; i++) {
            sum += slots[i] + (slots[i] / 1400) * 23;
        }
        return sum;
    }

    static class Participant {
        private final int no;

        Participant(int no) {
            this.no = no;
        }

        public void add(int timeSlot) {
            Integer old = messages.get(timeSlot);
            if (old == null) {
                old = 0;
            }
            messages.put(timeSlot, old + 1);
        }

        Map<Integer, Integer> messages = new HashMap<>();
    }
}
