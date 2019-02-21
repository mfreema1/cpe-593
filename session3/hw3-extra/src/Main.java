//@author: Mark Freeman
//@date: 2/11/2019
//@pledge: I pledge my honor that I have abided by the Stevens Honor System

import java.util.Random;
import java.util.function.Function;


import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //search for solutions between k = 0 and k = 1000
        int sortSize = 10000000;
        int optimalK = goldenMeanSearch(Main::timeFunction, 0, 200, sortSize);
        System.out.println("Optimal K for sort of size " + sortSize + " found to be " + optimalK);
    }

    //make a random array for each iteration and pass it to the quicksort function.  Time how long it takes to sort
    //the array.  Do this 1000 times with different arrays to eliminate the possibility that some k values get better
    //starting arrays than others.
    private static int iterations = 1;
    public static int timeFunction(int sortSize) {
        double buildup = 0;
        for(int i = 0; i < iterations; i++) {
            int[] data = createRandomArray(sortSize);
            long start = System.nanoTime();
            optimizedQuicksort(data);
            insertionSort(data); //finish it up
            long end = System.nanoTime();
            buildup += Math.log((end - start));
        }
        return (int)Math.round(Math.exp(buildup / iterations) / 1000000); //geometric mean with overflow guard in milliseconds
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


    //step 1: take the ln of all numbers
    //step 2: add all numbers
    //step 3: divide by n
    //step 4: raise to power of e
    public static int geometricMean(int[] data) {
        int buildup = 0;
        for(int i = 0; i < data.length; i++) {
            buildup += Math.log(data[i]) / data.length;
        }
        return (int)Math.round(Math.exp(buildup));
    }

    //just provide default values
    public static void optimizedQuicksort(int[] data) {
        optimizedQuicksort(data, 0, data.length - 1);
    }

    //run an optimized quicksort algorithm with insertion sort on buckets at or below
    //k elements
    // public static int k;
    private static int k;
    public static void optimizedQuicksort(int[] data, int left, int right) {
        if(left >= right || right - left + 1 <= k) {
            return;
        }

        int randomIndex = (int)(Math.random() * (right - left)) + left;
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
    public static int goldenMeanSearch(Function<Integer, Integer> func, int a, int b, int sortSize) {
        double phi = (Math.sqrt(5) + 1) / 2;
        int delta = (int)Math.round((b - a) / phi);
        int right = a + delta;
        int left = b - delta;

        k = left;
        int runTimeLeft = func.apply(sortSize);

        k = right;
        int runTimeRight = func.apply(sortSize);

        while(a < b) {
            if(runTimeLeft < runTimeRight) { //aim for the minimum
                b = right;
                right = left;
                delta = right - a;
                left = b - delta;

                //now shift the runtimes too
                runTimeRight = runTimeLeft;
                k = left;
                runTimeLeft = func.apply(sortSize);
                System.out.println("Tried: k = " + k + " with average runtime " + runTimeLeft + " ms");
            }
            else {
                //bring up the left, it is lower
                a = left;
                left = right;
                delta = b - left;
                right = a + delta;

                runTimeLeft = runTimeRight;
                k = right;
                runTimeRight = func.apply(sortSize);
                System.out.println("Tried: k = " + k + " with average runtime " + runTimeRight + " ms");
            }
        }
        //is it always left? Or do we need to take the min of the two?  It always seems to be left in tests
        //but I'm not sure
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