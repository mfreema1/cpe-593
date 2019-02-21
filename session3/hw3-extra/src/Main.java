//@author: Mark Freeman
//@date: 2/11/2019
//@pledge: I pledge my honor that I have abided by the Stevens Honor System

import java.util.Random;
import java.util.function.BiFunction;


import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //search for solutions between k = 0 and k = 1000 on an array of 10 million elements
        int sortSize = 10000000, lowerSearchBound = 0, upperSearchBound = 1000;
        int optimalK = goldenMeanSearch(Main::timeFunction, lowerSearchBound, upperSearchBound, sortSize);
        System.out.println("Optimal K for sort of size " + sortSize + " found to be " + optimalK);
    }

    //time the knuth-optimized quicksort, now that we're doing only 1 iteration, no need to take geometric mean
    public static int timeFunction(int bucketValue, int sortSize) {
        int[] data = createRandomArray(sortSize);
        long start = System.nanoTime();
        k = bucketValue;
        optimizedQuicksort(data);
        insertionSort(data); //finish it up
        long end = System.nanoTime();
        return (int)Math.round((end - start) / 1000000); //geometric mean with overflow guard in milliseconds
    }

    //perform a standard insertion sort
    public static void insertionSort(int[] data) {
        for(int i = 1; i < data.length; i++) {
            int j = i;
            while(j > 0 && data[j] < data[j - 1]) {
                int temp = data[j - 1];
                data[j - 1] = data[j];
                data[j] = temp;
                j--;
            }
        }
    }

    //just provide default values
    public static void optimizedQuicksort(int[] data) {
        optimizedQuicksort(data, 0, data.length - 1);
    }

    //run an optimized quicksort algorithm skipping buckets of size k or less
    private static int k; //doesn't change through recursive calls
    public static void optimizedQuicksort(int[] data, int left, int right) {
        if(left >= right || right - left + 1 <= k) {
            return;
        }

        int randomIndex = random.nextInt(right - left) + left;
        int pivot = data[randomIndex];
        int i = left, j = right;
        while(true) {
            while (data[i] < pivot) {
                i++;
            }
            while (data[j] > pivot) {
                j--;
            }

            //avoid swapping two of the same elements infinitely
            while(data[i] == data[j] && i < j) {
                j--;
            }

            if (i >= j) {
                optimizedQuicksort(data, left, j);
                optimizedQuicksort(data, j + 1, right);
                return;
            }
            else { //not necessary but makes clearer
                swap(data, i, j);
            }
        }
    }

    //swap indices of array
    public static void swap(int[] data, int left, int right) {
        int temp = data[left];
        data[left] = data[right];
        data[right] = temp;
    }

    //make a random array of n elements consisting of elements between 1 and n inclusive
    private static Random random = new Random();
    public static int[] createRandomArray(int number) {
        //both bounds inclusive
        int[] arr = new int[number];
        for(int i = 0; i < number; i++) {
            arr[i] = random.nextInt(number) + 1;
        }
        return arr;
    }

    //run a golden mean search to find the minimum of the given function between the two bounds a and b
    //doesn't need to be a bifunction, we just pass k into the time function to set it as static right before
    //jumping into quicksort to avoid passing it in the recursive calls
    public static int goldenMeanSearch(BiFunction<Integer, Integer, Integer> func, int a, int b, int sortSize) {
        double phi = (Math.sqrt(5) + 1) / 2;
        int delta = (int)Math.round((b - a) / phi);
        int right = a + delta;
        int left = b - delta;

        int runTimeLeft = func.apply(left, sortSize);
        int runTimeRight = func.apply(right, sortSize);

        while(a < b) {
            if(runTimeLeft < runTimeRight) { //aim for the minimum
                b = right;
                right = left;
                delta = right - a;
                left = b - delta;

                //now shift the runtimes too
                runTimeRight = runTimeLeft;
                runTimeLeft = func.apply(left, sortSize);
                System.out.println("Tried: k = " + left + " with average runtime " + runTimeLeft + " ms");
            }
            else {
                //bring up the left, it is lower
                a = left;
                left = right;
                delta = b - left;
                right = a + delta;

                runTimeLeft = runTimeRight;
                runTimeRight = func.apply(right, sortSize);
                System.out.println("Tried: k = " + right + " with average runtime " + runTimeRight + " ms");
            }
        }
        //print the optimal solution -- one will not have moved as the other runs off
        if(runTimeLeft < runTimeRight) {
            System.out.println("Found optimal solution to be: k = " + left + " with average runtime " + runTimeLeft + " ms");
            return left;
        }
        else {
            System.out.println("Found optimal solution to be: k = " + right + " with average runtime " + runTimeRight + " ms");
            return right;
        }
    }
}