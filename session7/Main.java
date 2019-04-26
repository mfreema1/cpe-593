import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            File f = new File("hw5.dat");
            Matrix m = new Matrix(f);
            double[] augVector = Matrix.getAugmentationVector(f);
            System.out.println(Arrays.toString(m.solve(augVector)));
        }
        catch(FileNotFoundException e) {
            System.out.println("Could not load file");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}