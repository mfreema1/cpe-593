//@author: Mark Freeman
//@pledge: I pledge my honor that I have abided by the Stevens Honor System.

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("hw3.dat"));
        int n = in.nextInt();
        int[] nums = new int[n];
        for(int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
        }
        System.out.println(Arrays.toString(nums));
        quicksort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
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
}
