// An improvement over the brute-force solution that utilizes bottom-up
// dynamic programming.  It runs in O(m*n) time.
public class Main {

    // Euler is somewhat deceptive.  Their "2x2" grid of boxes is
    // actually a 3x3 grid of points if you think about it -- just up 1.
    private static final int NUM_ROWS = 21;
    private static final int NUM_COLS = 21;

    public static void main(String[] args) {

        // Make a table to represent our field.  Use longs to avoid
        // the inevitable overflow as you approach 20x20
        Long[][] table = new Long[NUM_ROWS][NUM_COLS];

        // We know that any field with only one row or only one column
        // only has one way to traverse -- straight there.  Hence,
        // fill 1's for the first row and col
        for(int i = 0; i < table[0].length; i++)
            table[0][i] = 1L;
        for(int i = 0; i < table.length; i++)
            table[i][0] = 1L;

        // Since we can only move to the right and down, the number of
        // ways to get to a square is the sum of the number of ways to get
        // to the box above it and the number of ways to get to the box to the
        // left of it -- makes this a bottom-up solution
        for(int i = 1; i < table.length; i++) {
            for(int j = 1; j < table[0].length; j++) {
                table[i][j] = table[i - 1][j] + table[i][j - 1];
            }
        }
        System.out.println(table[NUM_ROWS - 1][NUM_COLS - 1]);
    }
}
