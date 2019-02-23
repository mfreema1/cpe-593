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
        insertionSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void insertionSort(int[] data) {
        //start at the beginning of the list and inch towards the end, inserting the new element
        //at the right place
        for(int i = 1; i < data.length; i++) {
            int j = i;
            while(j > 0 && data[j] < data[j - 1]) { //can get all the way to the front if need be
                int temp = data[j - 1];
                data[j - 1] = data[j];
                data[j] = temp;
                j--;
            }
        }
    }
}
