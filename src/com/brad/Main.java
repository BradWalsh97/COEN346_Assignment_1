package com.brad;

import javax.swing.text.Style;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Main {
    public  static class MaxThreadCounter{
        public static int MaxThreadCount;
        public synchronized int getMaxThreadCounter() {return this.MaxThreadCount;}
        public synchronized void setMaxThreadCounter(int max) {this.MaxThreadCount = max;}
    }
    private static MaxThreadCounter MaxThreads = new MaxThreadCounter();

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
        //Debug: Output light bulbs
//        for (char c : lightBulbs)
//            System.out.println(c);

        if (arraySize != lightBulbs.length) {
            System.out.println("Array size does not match inputted array. Please try again."); //todo: input error checking
            return;
        } else {
            FindDefective(lightBulbs, 0, lightBulbs.length - 1);
            System.out.println("This program took " + MaxThreads.getMaxThreadCounter() + " threads.");
        }

    }

    public static void FindDefective(int[] array, int startingIndex, int lastIndex) throws InterruptedException {
        int pivot = startingIndex + (lastIndex - startingIndex) / 2;

        if ((pivot == startingIndex) && array[pivot] == 0) {
            ArrayList<Integer> positions = new ArrayList<Integer>();
            positions.add(startingIndex);
            positions.forEach((n) -> System.out.println("Defective bulb in position: " + n));
        }

        if (noDefective(array)) {
            System.out.println("All 1s");
            return;
        }
        //TODO: Try to make this global and mutex it
        int threadCount = ManagementFactory.getThreadMXBean().getThreadCount();
        if (threadCount > MaxThreads.getMaxThreadCounter())
            MaxThreads.setMaxThreadCounter(threadCount);

        // Traverse left side & right side of the array if possible
        if (pivot - startingIndex >= 1 && lastIndex - pivot >= 1) {
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
            leftArrayThread.join();
        }

        //Traverse just left side of the array
        else if(pivot - startingIndex >= 1){
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
            leftArrayThread.start();
            leftArrayThread.join();
        }
        // Traverse just  right side of the array
        else if (lastIndex - pivot >= 1) {
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
            rightArrayThread.start();
            rightArrayThread.join();
        }



    }

    // Check if the array has no defective elements
    public static boolean noDefective(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                return false;
            }
        }
        return true;
    }
    /*

    private static void FindDefective(char[] lightBulbs, int arraySize) throws InterruptedException {
        ArrayList<Integer> emptyList = new ArrayList<Integer>();
        ArrayList<Integer> deadBulbs = FindDefective(lightBulbs, arraySize, (int) Math.ceil((double) arraySize / 2), emptyList);
        System.out.println("There are " + deadBulbs.size() + " dead bulbs in the array.");
        System.out.println("The dead bulbs are: ");
        for (int i = 0; i < deadBulbs.size(); i++) {
            if(deadBulbs.get(i) == -1)
                continue;
            System.out.println("Bulb " + deadBulbs.get(i));
        }
        System.out.println("Size: " + deadBulbs.size());

    }



    //return true if there is a zero present at the base array. False if there's ones.
    private static ArrayList<Integer> FindDefective(char[] lightBulbs, int arraySize, int pivot, ArrayList<Integer> list) throws InterruptedException {
        ArrayList<Integer> emptyList = new ArrayList<>();
//        int[] currentReturn;
//        int[] leftValues;
//        int[] rightValues;

        //check if array has zeros
        boolean containsZero = false;
        for (char c : lightBulbs) {
            if (c == '0') {
                containsZero = true;
                break;
            }
        }

        if (containsZero && arraySize >= 2) {
            // Function to split array into two parts in Java
            char[] leftBulbs = new char[(pivot + 1) / 2];
            char[] rightBulbs = new char[arraySize - leftBulbs.length];

            System.arraycopy(lightBulbs, 0, leftBulbs, 0, leftBulbs.length);
            System.arraycopy(lightBulbs, leftBulbs.length, rightBulbs, 0, rightBulbs.length);

//            list.addAll(FindDefective(leftBulbs, leftBulbs.length, (int) Math.ceil((double) arraySize / 2), list));
//            list.addAll(FindDefective(rightBulbs, rightBulbs.length, (int) Math.ceil((double) arraySize / 2), list));
            emptyList.addAll(FindDefective(leftBulbs, leftBulbs.length, (int) Math.ceil((double) arraySize / 2), list));
            emptyList.addAll(FindDefective(rightBulbs, rightBulbs.length, (int) Math.ceil((double) arraySize / 2), list));
            list.addAll(emptyList);
            //list.addAll(emptyList);

//            Thread leftThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        list.addAll(FindDefective(leftBulbs, leftBulbs.length, (int) Math.ceil((double) arraySize / 2), list));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            Thread rightThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        list.addAll(FindDefective(rightBulbs, rightBulbs.length, (int) Math.ceil((double) arraySize / 2), list));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            leftThread.start();
//            rightThread.start();
//            leftThread.join();
//            rightThread.join();
        } else if (containsZero && arraySize == 1) {
            list.add(pivot);
            return list;
        }

        list.add(-1);
        return list;
    }
    */
}

//return the pivot as position as variable