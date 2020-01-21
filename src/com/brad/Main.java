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

        for (char c:lightBulbs) {
            System.out.println(c);
        }

    }
}
