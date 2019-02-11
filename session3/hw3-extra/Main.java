import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        Function<Double, Double> function = Main::exampleFunc;
        System.out.println(goldenMeanSearch(function, 0, 1000, .005));
    }

    //an upside-down parabola shifted to the right 100 units
    public static double exampleFunc(double input) {
        return (Math.pow((input - 100), 2)) * -1 + 100;
    }

    public static double timedQuicksort(int[] data) {
        return 0;
    }

    public static void changeOverQuicksort(int[] data) {
        
    }

    /**
     * 
     * @param function -- the quicksort function we will be evaluating
     * @param a -- the lower bound, will start at 0
     * @param b -- the upper bound, will start at 2000
     * @param epsilon -- the tolerance before we quit
     */
    public static double goldenMeanSearch(Function<Double, Double> func, double a, double b, double epsilon) {
        double phi = (Math.sqrt(5) + 1) / 2;
        double delta = (b - a) / phi;
        double right = a + delta;
        double left = b - delta;

        while(a < b) {
            System.out.println("Left: " + left + ", right: " + right);
            System.out.println("Delta: " + delta);
            if(func.apply(left) > func.apply(right)) {
                //bring up the right, it is lower
                b = right;
                right = left;
                delta = right - a; //just another way to calculate the delta
                left = b - delta;
            }
            else {
                //bring up the left, it is lower
                a = left;
                left = right;
                delta = b - left;
                right = a + delta;
            }
        }
        return func.apply(left);
    }
}