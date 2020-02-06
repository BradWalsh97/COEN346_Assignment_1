package com.brad;

import java.io.*;
import java.nio.file.Paths;

public class Main {
    protected static MaxThreadCounter MaxThreads = new MaxThreadCounter();

    public static void main(String[] args) throws IOException, InterruptedException {

        //Start by getting the input file and creating an array consisting of all the light bulbs
        File file = new File(Paths.get("input.txt").toAbsolutePath().toString());
        int arraySize;
        BufferedReader in = new BufferedReader(new FileReader(file));
        arraySize = Integer.parseInt(in.readLine());
        int[] lightBulbs = new int[arraySize];
        System.out.println("There are " + lightBulbs.length + " light bulbs in this example.");
        String str;
        int lightIter = 0;
        while ((str = in.readLine()) != null) {
            if (lightIter > arraySize)
                break;
            lightBulbs[lightIter] = Integer.parseInt(str);
            lightIter++;
        }

        MaxThreads.setMaxThreadCounter(0);

        if (arraySize != lightBulbs.length) {
            System.out.println("Array size does not match inputted array. Please try again.");
            return;
        } else {
            FindDefective(lightBulbs, 0, lightBulbs.length - 1);
            System.out.println("This program took " + MaxThreads.getMaxThreadCounter() + " threads.");
        }

    }

    public static void FindDefective(int[] array, int startingIndex, int lastIndex) throws InterruptedException {
        int pivot = startingIndex + (lastIndex - startingIndex) / 2;

        if ((lastIndex == startingIndex) && (array[pivot] == 0)) {
                System.out.println("Defective bulb in position: " + pivot);
        }

        else if (noDefective(array, startingIndex, lastIndex)) {
            return;
        }
        
        else {
            Thread leftArrayThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        FindDefective(array, startingIndex, pivot);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread rightArrayThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        FindDefective(array, pivot + 1, lastIndex);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            leftArrayThread.start();
            rightArrayThread.start();
            leftArrayThread.join();
            rightArrayThread.join();

        }

        int threadCount = java.lang.Thread.activeCount();
        if (threadCount > MaxThreads.getMaxThreadCounter())
            MaxThreads.setMaxThreadCounter(threadCount);

    }

    // Check if the array has no defective elements (Are there any zeros in the array?)
    public static boolean noDefective(int[] array, int startingIndex, int lastIndex) {
        for (int i = startingIndex; i <= lastIndex; i++) {
            if (array[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public  static class MaxThreadCounter{
        public static int MaxThreadCount;
        public synchronized int getMaxThreadCounter() {return this.MaxThreadCount;}
        public synchronized void setMaxThreadCounter(int max) {this.MaxThreadCount = max;}
    }
}