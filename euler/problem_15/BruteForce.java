// The most obvious way is a brute-force recursive approach that mimics use-it
// or lose-it.  At each point, just try to go down or right and add up the number
// of ways.  Complexity is O(2^(m*n)), will not finish for large values
public class BruteForce {

    private static final int NUM_ROWS = 3;
    private static final int NUM_COLS = 3;
    public static void main(String[] args) {
        System.out.println(lattice(NUM_ROWS, NUM_COLS));
    }

    public static Long lattice(int row, int col) {
        // only path is straight there with one row or col
        if(row == 1 || col == 1)
            return 1L;
        
        // sort of like use_it or lose_it
        Long go_right = lattice(row, col - 1);
        Long go_down = lattice(row - 1, col);
        return go_right + go_down;
    }
}