import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] arr =createRandomArray(1000);
        System.out.println(Arrays.toString(arr));
        quicksort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quicksort(int[] data, int left, int right) {
        if(left >= right) {
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
                quicksort(data, left, j);
                quicksort(data, j + 1, right);
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

    public static int[] createRandomArray(int number) {
        int lowerBound = -2000, upperBound = 2000; //both inclusive
        int[] arr = new int[number];
        for(int i = 0; i < number; i++) {
            arr[i] = (int)((Math.random() * (upperBound - lowerBound + 1))) + lowerBound;
        }
        return arr;
    }
}
