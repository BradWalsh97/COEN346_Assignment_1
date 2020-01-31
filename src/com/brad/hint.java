package com.brad;

import java.util.ArrayList;

public class hint {

    public static void main(String[] args) throws InterruptedException {

        // This array should be created from the input file. Here I'm hardcoding it into
        // the code
        int[] inputArray = new int[] { 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0 };

        FindDefective(inputArray, 0, inputArray.length - 1);
    }

    public static void FindDefective(int[] array, int startingIndex, int lastIndex) {
        int pivot = startingIndex + (lastIndex - startingIndex) / 2;

        if ((pivot == startingIndex) && array[pivot] == 0) {
            ArrayList<Integer> positions = new ArrayList<Integer>();
            positions.add(startingIndex);
            positions.forEach((n) -> System.out.println(n));
        }

        if (noDefective(array)) {
            System.out.println("All 1s");
            return;
        }

        // Traverse left side of the array
        if (pivot - startingIndex >= 1) {
            FindDefective(array, startingIndex, pivot);
        }

        // Traverse right side of the array
        if (lastIndex - pivot >= 1) {
            FindDefective(array, pivot + 1, lastIndex);
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
}
