package com.brad;

import java.io.*;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        //Start by getting the input file and creating an array consisting of all the light bulbs
        File file = new File(Paths.get("input.txt").toAbsolutePath().toString());
        int arraySize;
        BufferedReader in = new BufferedReader(new FileReader(file));
        arraySize = Integer.parseInt(in.readLine());
        Character[] lightBulbs = new Character[arraySize];
        System.out.println("There are " + lightBulbs.length + " light bulbs in this example.");
        String str;
        int lightIter = 0;
        while ((str = in.readLine()) != null) {
            lightBulbs[lightIter] = str.charAt(0);
            lightIter++;
        }

        //Output light bulbs, uncomment for debugging
        for (char c:lightBulbs)
            System.out.println(c);

        FindDefective(lightBulbs, arraySize, (int) Math.ceil((double)arraySize/2));

    }

    private static void FindDefective(Character[] lightBulbs, int arraySize, int pivot) {

        //start by searching the character array for a zero. If its there, create a right and left
        //sub array and assign them each a thread
        boolean containsZero = false;
        for (char c : lightBulbs) {
            if (c == '0') {
                containsZero = true;
                break;
            }
        }
        if(containsZero){
            //if there is a zero, we divide the array based on the pivot
            Character[] leftBulbs = new Character[pivot];
            Character[] rightBulbs = new Character[pivot];
            for (int i = 0; i < pivot; i++) {
                leftBulbs[i] = lightBulbs[i];
                rightBulbs[lightBulbs.length/2 - i - 1] = lightBulbs[lightBulbs.length - i - 1];//todo: check that it should be -1
            }
            System.out.println("Left");
            for (char c:leftBulbs) System.out.println(c);
            System.out.println("Right");
            for (char c:rightBulbs) System.out.println(c);

            //FindDefective();
        }
        if (!containsZero) {
            System.out.println("Array Contains no zeros");
        }

    }
}
