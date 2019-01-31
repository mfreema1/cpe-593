import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        int n = 20;
        int r = 10;
        System.out.println(factorialMemoized(new HashMap<>(){{ put(0, 1); }}, 7));
    }

    //TODO: Create a factorial which takes 2 arguments, a start and stop point
    //for example, 10! with a stoppage at 8

    /**
     * Compute a partial factorial between two numbers
     * @param start - the number to start counting down from
     * @param stop - the number to stop at when counting down (inclusive)
     * @return the product of all numbers between and including start and stop
     */
    private static int earlyExitFactorial(int start, int stop) {
        //just a simple multiplication with a buildup
        int buildup = 1;
        for(int i = start; i >= stop; i--) {
            buildup *= i;
        }
        return buildup;
    }

    /**
     * @param n - the number to take the factorial of
     * @param knownFactorials - a map of all the factorials we have memoized.  It is carried
     *                        across calls to improve efficiency in future calls from choose()
     * @return
     */
    private static int factorialMemoized(Map<Integer, Integer> knownFactorials, int n) {
        Stack<Integer> numbersToProcess = new Stack<>();
        numbersToProcess.push(n); //pull the lever, Kronk!

        int buildup = 1;
        while(!numbersToProcess.isEmpty()) {
            System.out.println(numbersToProcess);
            int currentNumber = numbersToProcess.pop();
            //special meaning: a factorial has finished processing, now we may place it in the map and restart buildup
            if(currentNumber == -1) {
                //the number directly before the zero on the stack is the one that pushed all of these,
                //pop that one as well, multiply it with the buildup, and place it in the map
                int callingNumber = numbersToProcess.pop();
                buildup *= callingNumber;
                knownFactorials.put(callingNumber, buildup);
                buildup = callingNumber;
            }
            else if(knownFactorials.get(currentNumber) == null) {
                //push all factorials onto the stack with a leading zero to signify the end of that factorial
                numbersToProcess.push(currentNumber);
                numbersToProcess.push(-1);
                for (int i = 0; i < currentNumber; i++) {
                    numbersToProcess.push(i);
                }
            }
            //we got a hit on the hash map, the buildup is that number, skip that number of items
            //on the stack
            else {
                buildup = knownFactorials.get(currentNumber);
                System.out.println(currentNumber);
                for(int i = 0; i >= currentNumber; i++) {
                    numbersToProcess.pop();
                }
            }
        }
        //by this point, we must know the factorial
        return knownFactorials.get(n);
    }

    /**
     *
     * @param n - the number of items in the population
     * @param r - the number of items that we are pulling from the population
     * @return n choose r
     * TODO: Fix the arithmetic of this.  When computing a factorial division, we don't actually need to calculate every
     * factorial.  We can perform the subtraction and take the higher
     */
    public static int choose(int n, int r) {
//        Map<Integer, Integer> knownFactorials = new HashMap<>(){{ put(0, 1); put(1, 1); }};
//        int nFactorial = factorialMemoized(knownFactorials, n);
//        int rFactorial = factorialMemoized(knownFactorials, r);
//        int nMinusRFactorial = factorialMemoized(knownFactorials, n - r);
//        return nFactorial / (rFactorial * nMinusRFactorial);
        //we want to eliminate as many factorials off the bat, both must be smaller than n though so we can
        //take the bigger one out of n and compute a partial factorial on it.  This way we compute a full factorial
        //only on the smaller number on the bottom of the fraction
//        int top, bottom;
//        if(n - r > r) {
//            top = earlyExitFactorial(n, n - r + 1);
//            bottom = factorialMemoized(new HashMap<>(){{ put(0, 1); }}, r);
//        }
//        else {
//            top = earlyExitFactorial(n, r + 1);
//            bottom = factorialMemoized(new HashMap<>(){{ put(0, 1); }}, n - r)
//        }

        int top = earlyExitFactorial(n, Math.max(n - r, r) + 1);
        int bottom = factorialMemoized(new HashMap<>(){{ put(0, 1); }}, Math.min(r, n - r));
        return top / bottom;
    }
}
