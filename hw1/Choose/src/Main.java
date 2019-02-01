import java.util.HashMap;
import java.util.Map;

//class choosePair {
//
//    int n;
//    int r;
//
//    public choosePair(int n, int r) {
//        this.n = n;
//        this.r = r;
//    }
//}

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            //chooseSlow(150, 5); //you'll be here awhile!
            choose(150, 5);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime));
    }

    //just a utility function to avoid having to write String.format over and over
    private static String parenthesize(int first, int second) {
        return String.format("(%d,%d)", first, second);
    }

    //memoization really helps with huge inputs in terms of speed.  We avoid repeating operations that we have
    //previously done
    private static Map<String, Integer> memo = new HashMap<>();
    public static int choose(int n, int r) {
        //derived from https://en.wikipedia.org/wiki/Binomial_coefficient section "Recursive formula"
        //initial values to watch for
        if(n == r || r == 0)
            return 1;
        //autobox so we don't have to call .get(parenthesize()) twice
        Integer memoResult = memo.get(parenthesize(n, r));
        if(memoResult != null)
            return memoResult;
        int calculatedResult = choose(n - 1, r - 1) + choose(n - 1, r);
        memo.put(parenthesize(n, r), calculatedResult);
        return calculatedResult;
    }

//    public static int choose(int n, int r) {
//        Map<choosePair, Integer> memo = new HashMap<>();
//        Queue<choosePair> pairs = new ArrayDeque<>();
//        choosePair root = new choosePair(n, r);
//        pairs.offer(root);
//        int buildup = 0;
//        while(!pairs.isEmpty()) {
//            choosePair pair = pairs.poll();
//            if(pair.n == pair.r || pair.r == 0)
//                buildup += 1;
//            else if(memo.get(pair) != null){
//                buildup += memo.get(pair);
//            }
//            else {
//                pairs.offer(new choosePair(pair.n - 1,pair.r - 1));
//                pairs.offer(new choosePair(pair.n - 1, pair.r));
//            }
//        }
//        return memo.get(root);
//    }

    //tricky function, seems fine on smaller n choose r values, but will fail fantastically for larger values of n
    public static int chooseSlow(int n, int r) {
        if(n == r || r == 0)
            return 1;
        return chooseSlow(n - 1, r - 1) + chooseSlow(n - 1, r);
    }
}
