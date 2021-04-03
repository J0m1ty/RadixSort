/*  Jonathan GS  *
*    RadixSort   *
*    3/18/2021   */

// Radix Sort v2 w/ Speed Comparisons
// Inspiration taken from https://www.youtube.com/watch?v=ujb2CIWE8zY <-- very good at explaining the algorithm

// This class uses Radix Sort to sort a random sample of integers
// It also runs the same set of integers on Bubble Sort and Arrays.sort()
// The speeds for all three sorts are recorded and printed
// Note: Arrays.sort() uses uses Mergesort, Insertion sort, or dual-pivot Quicksort

import java.util.*;

public class RadixSort {
    private static final int NUM_OF_VALUES = 10_000_000; // Sample size of sort
    private static final int RANGE = 100000000; // Will sorting numbers from 0 to RANGE - 1

    // Main method
    public static void main(String[] args) {

        // Explain and describe program
        System.out.println(" -- Radix Sort v2 w/ Speed Comparisons -- ");
        System.out.println("Sorting "+NUM_OF_VALUES+" integers from range 0-"+(RANGE-1)+"\n");

        StopWatch sw = new StopWatch();
        int[] myVals = getRandomArray();

        /* RADIX SORT */
        System.out.print("Radix Sort ...");

        // Start the timer, sort, stop the timer
        sw.start();
        sort(myVals);
        sw.stop();

        // Print the result
        System.out.println("\rRadix Sort took "+(double)sw.getElapsedTime()/1000+
                " seconds");

        /* Arrays.sort() */
        System.out.print("Arrays.sort() ...");
        myVals = getRandomArray();
        sw.start();
        Arrays.sort(myVals);
        sw.stop();
        System.out.println("\rArrays.sort() took "+(double)sw.getElapsedTime()/1000+
                " seconds");

        /* Bubble.sort() */
        System.out.println("Bubble Sort ... (if the program is taking too long, hit control-c to stop it)");
        myVals = getRandomArray();
        sw.start();
        for (int i = 0; i < myVals.length; i++) {
            for (int j = i + 1; j < myVals.length; j++) {
                int temp;
                if (myVals[i] > myVals[j]) {
                    temp = myVals[i];
                    myVals[i] = myVals[j];
                    myVals[j] = temp;
                }
            }
            System.out.print("\r "+Math.floor((double)i/NUM_OF_VALUES*10000)/100+"%");
        }
        sw.stop();
        System.out.println("\nBubble Sort took "+(double)sw.getElapsedTime()/1000+
                " seconds");
    }

    // Radix Sort, ascending
    public static void sort(int[] input) {

        // Creates a temporary array
        int[] output = new int[input.length];

        // Loops through each possible digit, max of 9
        for (int digit = 1; digit < 10; digit++) {

            // Holds the frequency of each digit
            int[] counts = new int[10];

            // Gets the frequency of each digit
            for (int i : input) {
                counts[getDigit(i, digit)]++;
            }

            // Calculates the prefix sort of the frequencies
            prefixSort(counts);

            // Fills the output array based on the counts array
            for (int i = input.length - 1, j = 0; i >= 0; i--, j++) {
                output[--counts[getDigit(input[i], digit)]] = input[i];
            }

            // Sets the output array to the input array
            for (int i = 0; i < input.length; i++) {
                input[i] = output[i];
                output[i] = 0; // Clears the output array
            }
        }
    }

    // Returns the digit at the Nth position in an int
    private static int getDigit(int number, int n) {
        return (int) ((number / Math.pow(10, n - 1)) % 10);
    }

    // Gives the prefix sort of a list, each item is summed with the item before it
    private static void prefixSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            arr[i] += arr[i-1];
        }
    }

    //Creates and returns a random array
    private static int[] getRandomArray() {
        Random gen = new Random(0);

        // Fills an array with random ints
        int[] out =  new int[NUM_OF_VALUES];
        for (int i = 0; i < NUM_OF_VALUES; i++) {
            out[i] = gen.nextInt(RANGE);
        }

        return out;
    }
}

class StopWatch {
    private long startTime;
    private long endTime;

    // Constructor
    public StopWatch() {
        startTime = System.currentTimeMillis();
        endTime = 0;
    }

    //Unused methods
    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    // Calls for the start/stop of the stopwatch
    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        endTime = System.currentTimeMillis();
    }

    //Gets the time after it's been stopped
    public long getElapsedTime() {
        return endTime-startTime;
    }

    //Gets the time without being stopped
    public long progress() {
        return System.currentTimeMillis()-startTime;
    }
}