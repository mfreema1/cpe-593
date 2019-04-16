public class Matrix {

    public int rows;
    public int cols;
    private double[] m;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(int rows, int cols, double val) {
        this(rows, cols);
        for(int i = 0; i < rows * cols; i++) {
            m[i] = val;
        }
    }

    public double get(int r, int c) {
        return m[r*cols + c];
    }

    public void set(int r, int c, double val) {
        m[r*cols + c] = val;
    }

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

    //make a matrix object and read in from the file
    //Ax = B
    //x is a vector of some values
    //full pivoting of matrix homework
}