//@author: Mark Freeman
//@date: 2/11/2019
//@pledge: I pledge my honor that I have abided by the Stevens Honor System

import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        //search for solutions between k = 0 and k = 1000
        goldenMeanSearch(Main::timeFunction, 0, 10000);
    }

    //run a sort of 10,000 elements 1000 times.  We'll use this to hunt down our optimal solution
    public static int timeFunction(int k) {
        return timeFunction(10000, k, 1000, -1000, 1000);
    }

    //make a random array for each iteration and pass it to the quicksort function.  Time how long it takes to sort
    //the array.  Do this 1000 times with different arrays to eliminate the possibility that some k values get better
    //starting arrays than others.
    public static int timeFunction(int sortSize, int k, int iterations, int lowerBound, int upperBound) {
        long buildup = 0;
        for(int i = 0; i < iterations; i++) {
            int[] data = createRandomArray(sortSize, lowerBound, upperBound);
            long start = System.nanoTime();
            optimizedQuicksort(data, k);
            long end = System.nanoTime();
            buildup += end - start;
        }
        return (int)((buildup / iterations) / 1000); //average time in milliseconds
    }

    //perform a standard insertion sort, but only between a select pair of indices
    public static void sectionedInsertionSort(int[] data, int left, int right) {
        for(int i = left + 1; i <= right; i++) {
            int j = i;
            while(j > left && data[j] < data[j - 1]) {
                int temp = data[j - 1];
                data[j - 1] = data[j];
                data[j] = temp;
                j--;
            }
        }
    }

    //just provide default values
    public static void optimizedQuicksort(int[] data, int k) {
        optimizedQuicksort(data, 0, data.length - 1, k);
    }

    //run an optimized quicksort algorithm with insertion sort on buckets at or below
    //k elements
    public static void optimizedQuicksort(int[] data, int left, int right, int k) {
        if(left >= right) {
            return;
        }

        //cutoff at k elements, sort this by insertion sort
        if(right - left + 1 <= k) {
            sectionedInsertionSort(data, left, right);
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
                optimizedQuicksort(data, left, j, k);
                optimizedQuicksort(data, j + 1, right, k);
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

    //make a random array of 'number' elements consisting of numbers between the two bounds
    public static int[] createRandomArray(int number, int lowerBound, int upperBound) {
        //both bounds inclusive
        int[] arr = new int[number];
        for(int i = 0; i < number; i++) {
            arr[i] = (int)((Math.random() * (upperBound - lowerBound + 1))) + lowerBound;
        }
        return arr;
    }

    //run a golden mean search to find the minimum of the given function between the two bounds a and b
    public static void goldenMeanSearch(Function<Integer, Integer> func, int a, int b) {
        double phi = (Math.sqrt(5) + 1) / 2;
        int delta = (int)Math.round((b - a) / phi);
        int right = a + delta;
        int left = b - delta;

        int runTimeLeft = func.apply(left);
        int runTimeRight = func.apply(right);

        while(a < b) {
            if(runTimeLeft < runTimeRight) { //aim for the minimum
                b = right;
                right = left;
                delta = right - a;
                left = b - delta;

                //now shift the runtimes too
                runTimeRight = runTimeLeft;
                runTimeLeft = func.apply(left);
                System.out.println("Tried: k = " + left + " with average runtime " + runTimeLeft + " ms");
            }
            else {
                //bring up the left, it is lower
                a = left;
                left = right;
                delta = b - left;
                right = a + delta;

                runTimeLeft = runTimeRight;
                runTimeRight = func.apply(right);
                System.out.println("Tried: k = " + right + " with average runtime " + runTimeRight + " ms");
            }
        }
        //is it always left? Or do we need to take the min of the two?  It always seems to be left in tests
        //but I'm not sure
        if(runTimeLeft < runTimeRight) {
            System.out.println("Found optimal solution to be: k = " + left + " with average runtime " + runTimeLeft + " ms");
        }
        else {
            System.out.println("Found optimal solution to be: k = " + right + " with average runtime " + runTimeRight + " ms");
        }
    }
}