import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        goldenMeanSearch(Main::timeFunction, 0, 1000);
    }

    //an upside-down parabola shifted to the right and up 100 units
    public static double exampleFunc(double input) {
        return (Math.pow((input - 100), 2)) * -1 + 100;
    }

    //run a sort of 10,000 elements 1000 times.  We'll use this to hunt down our optimal solution
    public static int timeFunction(int k) {
        return timeFunction(10000, k, 10000, -1000, 1000);
    }

    public static int timeFunction(int sortSize, int k, int iterations, int lowerBound, int upperBound) {

        int[] data = createRandomArray(sortSize, lowerBound, upperBound);

        long buildup = 0;
        for(int i = 0; i < iterations; i++) {
            int[] copy = data.clone();
            long start = System.nanoTime();
            optimizedQuicksort(copy, k);
            long end = System.nanoTime();
            buildup += end - start;
        }
        return (int)((buildup / iterations) / 1000); //milliseconds
    }

    public static void sectionedInsertionSort(int[] data, int left, int right) {
        //start at the beginning of the list and inch towards the end, inserting the new element
        //at the right place
        for(int i = left + 1; i <= right; i++) {
            int j = i;
            while(j > left && data[j] < data[j - 1]) { //can get all the way to the front if need be
                int temp = data[j - 1];
                data[j - 1] = data[j];
                data[j] = temp;
                j--;
            }
        }
    }

    public static void optimizedQuicksort(int[] data, int k) {
        optimizedQuicksort(data, 0, data.length - 1, k);
    }

    /**
     *
     * @param data -- array to sort
     * @param left -- the left index
     * @param right -- the right index
     * @param k -- the cutoff at which we switch into insertion sort
     */
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

    public static void swap(int[] data, int left, int right) {
        int temp = data[left];
        data[left] = data[right];
        data[right] = temp;
    }

    public static int[] createRandomArray(int number, int lowerBound, int upperBound) {
        //both bounds inclusive
        int[] arr = new int[number];
        for(int i = 0; i < number; i++) {
            arr[i] = (int)((Math.random() * (upperBound - lowerBound + 1))) + lowerBound;
        }
        return arr;
    }

    /**
     * 
     * @param func -- the function of a single variable that we are evaluating (quicksort)
     * @param a -- the lower bound, will start at 0
     * @param b -- the upper bound, will start at 2000
     */
    public static void goldenMeanSearch(Function<Integer, Integer> func, int a, int b) {
        double phi = (Math.sqrt(5) + 1) / 2;
        int delta = (int)Math.round((b - a) / phi);
        int right = a + delta;
        int left = b - delta;

        int bestRunTime = Integer.MAX_VALUE, bestK = 0;
        int runTimeLeft = func.apply(left);
        int runTimeRight = func.apply(right);

        while(a < b) {
            //TODO: see about pulling this out into functions
            if(runTimeLeft < runTimeRight) { //now aim for the minimum, just reverse!
                //bring up the right, it is lower
                b = right;
                right = left;
                delta = right - a; //just another way to calculate the delta
                left = b - delta;

                //now shift the runtimes too
                runTimeRight = runTimeLeft;
                runTimeLeft = func.apply(left);
                System.out.println("Tried: k = " + left + " with average runtime " + runTimeLeft);
                if(runTimeLeft < bestRunTime) {
                    bestRunTime = runTimeLeft;
                    bestK = left;
                }
            }
            else {
                //bring up the left, it is lower
                a = left;
                left = right;
                delta = b - left;
                right = a + delta;

                runTimeLeft = runTimeRight;
                runTimeRight = func.apply(right);
                System.out.println("Tried: k = " + right + " with average runtime " + runTimeRight);
                if(runTimeRight < bestRunTime) {
                    bestRunTime = runTimeRight;
                    bestK = right;
                }
            }
        }
        System.out.println("Best run was: k = " + bestK + " with average runtime " + bestRunTime + " ms");
    }
}