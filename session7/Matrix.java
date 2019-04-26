import java.util.*;
import java.io.*;

public class Matrix {

    public int rows;
    public int cols;
    private double[] m;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.m = new double[rows * cols];
    }

    public Matrix(int rows, int cols, double val) {
        this(rows, cols);
        for(int i = 0; i < rows * cols; i++) {
            this.m[i] = val;
        }
    }

    public Matrix(File f) throws FileNotFoundException {
        Scanner in = new Scanner(f);
        int size = in.nextInt();
        this.rows = size;
        this.cols = size;
        this.m = new double[rows * cols];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                set(i, j, in.nextInt());
            }
        }
        in.close();
    }

    public static double[] getAugmentationVector(File f) throws FileNotFoundException {
        Scanner in = new Scanner(f);
        int size = in.nextInt();
        for(int i = 0; i < size * size; i++, in.nextInt())
            ; //jump over all of the numbers used in the matrix

        double[] aug = new double[size];
        for(int i = 0; i < size; i++) {
            aug[i] = in.nextInt();
        }
        in.close();
        return aug;
    }

    //not the best way to do it, I'll look into another
    @Override
    public String toString() {
        StringJoiner inner = new StringJoiner("\t");
        StringJoiner outer = new StringJoiner("\n");
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                //format to 3 decimal places, otherwise it's just ugly
                inner.add(String.format("%.3f", (Double)get(i, j)));
            }
            outer.add(inner.toString());
            inner = new StringJoiner("\t");
        }
        return outer.toString();
    }

    public double get(int r, int c) {
        return m[r*cols + c];
    }

    public void set(int r, int c, double val) {
        m[r*cols + c] = val;
    }

    //I don't think these need to be static, but the spec says they should be.  Maybe
    //to more closely resemble an operator?
    //matrix addition, can only do on same size matrices
    public static Matrix add(Matrix m1, Matrix m2) throws Exception {
        if(m1.rows != m2.rows || m1.cols != m2.cols)
            throw new Exception("Cannot add two different sized matrices");
        Matrix m3 = new Matrix(m1.rows, m1.cols);
        for(int i = 0; i < m1.rows; i++) {
            for(int j = 0; j < m1.cols; j++) {
                m3.set(i, j, m1.get(i, j) + m2.get(i, j));
            }
        }
        return m3;
    }

    //matrix multiplication, can only do when m1.cols = m2.rows
    public static Matrix mult(Matrix m1, Matrix m2) throws Exception {
        if(m1.cols != m2.rows)
            throw new Exception("Cannot multiply, shapes do not align");
        Matrix m3 = new Matrix(m1.rows, m2.cols);
        for(int row = 0; row < m1.rows; row++) {
            for(int col2 = 0; col2 < m2.cols; col2++) {
                double sum = 0;
                for(int row2 = 0; row2 < m2.rows; row2++) {
                    sum += m1.get(row, col2) * m2.get(row2, col2);
                }
                m3.set(row, col2, sum);
            }
        }
        return m3;
    }

    public static Matrix copy(Matrix m1) {
        Matrix m2 = new Matrix(m1.rows, m1.cols);
        for(int i = 0; i < m1.rows; i++) {
            for(int j = 0; j < m1.cols; j++) {
                m2.set(i, j, m1.get(i, j));
            }
        }
        return m2;
    }

    private void swapRows(int r1, int r2) {
        for(int i = 0; i < cols; i++) {
            double temp = get(r2, i);
            set(r2, i, get(r1, i));
            set(r1, i, temp);
        }
    }

    private void swap(double[] arr, int i1, int i2) {
        double temp = arr[i2];
        arr[i2] = arr[i1];
        arr[i1] = temp;
    }

    private int getFirstRowWithNonzero(int rowsCompleted) throws Exception {
        for(int i = rowsCompleted; i < cols; i++) {
            for(int j = rowsCompleted; j < rows; j++) {
                if(get(j, i) != 0)
                    return j;
            }
        }
        throw new Exception("Solution could not be found");
    }

    private double getFirstNonzeroTermOnRow(int row) throws Exception {
        for(int i = 0; i < cols; i++) {
            double term = get(row, i);
            if(term != 0)
                return term;
        }
        throw new Exception("Row found to have only zeroes");
    }

    //solve matrix m with the augmentation matrix aug
    public double[] solve(double[] aug) throws Exception{
        if(rows != aug.length)
            throw new Exception("Incorrect lengths for matrix and augmentation vector");
        for(int i = 0; i < rows; i++) {

            //bring the row with the first non-zero term to the top of workable rows 
            int nextRow = getFirstRowWithNonzero(i);
            if(nextRow != i) {
                swapRows(nextRow, i);
                swap(aug, nextRow, i);
            }

            swapRows(getFirstRowWithNonzero(i), i); 
            double leadingTerm = getFirstNonzeroTermOnRow(i);

            //now divide the row and augmentation vector by the leading term
            for(int j = i; j < cols; j++) {
                set(i, j, get(i, j) / leadingTerm);
            }
            aug[i] = aug[i] / leadingTerm;

            //now add multiples of the previous row to zero out terms below the 1 we just created
            for(int j = i + 1; j < rows; j++) {
                double multiplier = get(j, i) * -1; //the term (i,i) is always 1 here
                for(int k = i; k < cols; k++) {
                    set(j, k, get(j, k) + get(i, k) * multiplier);
                }
                aug[j] += aug[i] * multiplier;
            }
        }

        //now we should have an upper-right triangular matrix and an augmentation vector that we can use to generate
        //our solution.  Walk the matrix (avoiding the diagonal) going upward and generate our solution from the bottom up.
        for(int i = rows - 2; i >= 0; i--) {
            double sum = 0;
            for(int j = i + 1; j < cols; j++) {
                sum += get(i, j) * aug[j];
            }
            aug[i] -= sum;
        }

        return aug;
    }
}