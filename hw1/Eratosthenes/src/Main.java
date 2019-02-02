/**
 * @author: Mark Freeman
 * @assignment: hw1a
 * @pledge: I pledge my honor that I have abided by the Stevens Honor System.
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.print("Enter a number: ");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.close();
        System.out.println(eratosthenes(n));
    }

    //use the sieve of eratosthenes, but an ~improved~ version
    private static int eratosthenes(int n) {
        if(n < 0) {
            System.out.println("Invalid number given");
            return -1;
        }
        if(n <= 3) {
            return n - 1;
        }
        //here's where the fun begins
        else {
            //make space for the odd numbers, set all true
            //O(n) time
            boolean[] numbers = new boolean[n];
            for(int i = 0; i < numbers.length; i++) {
                numbers[i] = true;
            }

            //go through and clear all the evens from 4 to n
            //O(n) time
            for(int i = 4; i < n; i += 2) {
                numbers[i] = false;
            }

            //go from 3 to n (only odds) and if the flag is still there, eliminate
            //every other multiple from i^2 to n, jumping by 2i.  We do this
            //because these are all odd numbers.  An odd number plus an odd number
            //is always even, so half of the time, if we jump by i, we will hit an even
            //number which cannot be prime.  Jumping by 2i avoids these checks.
            int count = 1;
            for(int i = 3; i < n; i += 2) { //O(n) time
                if(numbers[i]) {
                    count++;
                    if(i < Math.sqrt(n)) { //we don't take kindly to overflows 'round these parts
                        for (int j = i * i; j < n; j += 2 * i) { //O(n) with a good constant??
                            numbers[j] = false;
                        }
                    }
                }
            }
            return count;
        }
    }
}