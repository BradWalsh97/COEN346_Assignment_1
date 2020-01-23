package com.brad;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //Start by getting the input file and creating an array consisting of all the light bulbs
        File file = new File(Paths.get("input.txt").toAbsolutePath().toString());
        int arraySize;
        BufferedReader in = new BufferedReader(new FileReader(file));
        arraySize = Integer.parseInt(in.readLine());
        char[] lightBulbs = new char[arraySize];
        System.out.println("There are " + lightBulbs.length + " light bulbs in this example.");
        String str;
        int lightIter = 0;
        while ((str = in.readLine()) != null) {
            lightBulbs[lightIter] = str.charAt(0);
            lightIter++;
        }

        //Debug: Output light bulbs
//        for (char c : lightBulbs)
//            System.out.println(c);

        FindDefective(lightBulbs, arraySize, (int) Math.ceil((double) arraySize / 2));

    }

    private static void FindDefective(char[] lightBulbs, int arraySize, int pivot) throws InterruptedException {

        //start by searching the character array for a zero. If its there, create a right and left
        //sub array and assign them each a thread
        boolean containsZero = false;
        for (char c : lightBulbs) {
            if (c == '0') {
                containsZero = true;
                break;
            }
        }
        if (containsZero) {
            // Function to split array into two parts in Java
            char[] leftBulbs = new char[pivot / 2];
            char[] rightBulbs = new char[arraySize - leftBulbs.length];

            System.arraycopy(lightBulbs, 0, leftBulbs, 0, leftBulbs.length);
            System.arraycopy(lightBulbs, leftBulbs.length, rightBulbs, 0, rightBulbs.length);

            //Debug: print left and right sub arrays
            System.out.println(Arrays.toString(leftBulbs));
            System.out.println(Arrays.toString(rightBulbs));


            //make the recursive call to the next sub arrays. Create each call in its own thread
            Thread thread1 = new Thread(new Runnable(){
                public void run() {
                    try {
                        FindDefective(leftBulbs, leftBulbs.length, (int) Math.ceil((double) arraySize / 2) );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread thread2 = new Thread(new Runnable(){
                public void run() {
                    try {
                        FindDefective(rightBulbs, rightBulbs.length, (int) Math.ceil((double)rightBulbs.length/2));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
         }
        if (!containsZero) {
            System.out.println("Array Contains no zeros");
        }

    }
}
