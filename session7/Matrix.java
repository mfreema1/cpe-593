public class Matrix {

    private int rows;
    private int cols;
    private int[] m;

    public Matrix(int rows, int cols, double val) {
        this.rows = rows;
        this.cols = cols;
        for(int i = 0; i < rows * cols; i++) {
            m[i] = val;
        }
    }

    public int get(int r, int c) {
        return m[r*cols + c];
    }

    public void set(int r, int c, int val) {
        m[r*cols + c] = val;
    }

    //matrix addition, can only do on same size matrices
    public Matrix add(Matrix m) {

    }

    //
    public Matrix mult(Matrix m) {

    }

    //make a matrix object and read in from the file
    //Ax = B
    //x is a vector of some values
    //full pivoting of matrix homework
}