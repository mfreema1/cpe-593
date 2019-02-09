import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] arr = new int[] {5, 2, 4, 6, 1, 3};
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
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
