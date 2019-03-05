import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File f = new File("hullData.dat");
        Scanner in = new Scanner(f);

        //gather up the first point, set to max and min for x and y
        double dummy = in.nextDouble();
        double bigX = dummy, littleX = dummy;
        dummy = in.nextDouble();
        double bigY = dummy, littleY = dummy;

        //go make a big matrix of grow arrays
        int gridSize = 16;
        HullBox[][] matrix = new HullBox[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                matrix[i][j] = new HullBox();
            }
        }

        //take a pass over the data -- learn the bounds on it
        while(in.hasNextDouble()) {
            double newX = in.nextDouble();
            double newY = in.nextDouble();

            //update the known extrema
            bigX = Math.max(bigX, newX);
            littleX = Math.min(littleX, newX);
            bigY = Math.max(bigY, newY);
            littleY = Math.min(littleY, newY);
        }

        double xPerBox = (bigX - littleX) / gridSize;
        double yPerBox = (bigY - littleY) / gridSize;
        //now take a second pass and toss it all in
        Scanner again = new Scanner(f);
        while(again.hasNextDouble()) {
            double newX = again.nextDouble();
            double newY = again.nextDouble();
            double rowDouble = (newY - littleY) / yPerBox;
            double colDouble = (newX - littleX) / xPerBox;
            //TODO: There's got to be a better way to do this...
            if(rowDouble == Math.floor(rowDouble) && rowDouble != 0) //if it's an exact hit (like on 16 -- out of bounds) round down
                rowDouble--;
            if(colDouble == Math.floor(colDouble) && colDouble != 0)
                colDouble--;
            int row = (int)rowDouble;
            int col = (int)colDouble;
            matrix[row][col].add(new Point(newX,  newY));
        }

        System.out.println("Printing the size of each array: ");
        int[] arr = new int[gridSize];
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                arr[j] = matrix[i][j].size();
            }
            System.out.println(Arrays.toString(arr));
        }

        System.out.println("\nPrinting the perimeter: ");
        for(int i = 0; i < gridSize; i++) { //print first row
            System.out.println("---\nBox at position " + (i + 1));
            System.out.println(matrix[0][i]);
        }
        for(int i = 1; i < gridSize - 1; i++) { //print last column, except for first and last row
            System.out.println("---\nBox at position " + ((i + 1) * gridSize));
            System.out.println(matrix[i][gridSize - 1]);
        }
        for(int i = 0; i < gridSize; i++) {
            System.out.println("---\nBox at position " + (gridSize * gridSize - i));
            System.out.println(matrix[gridSize - 1][gridSize - 1 - i]);
        }
        for(int i = 1; i < gridSize - 1; i++) {
            System.out.println("---\nBox at position " + (gridSize * (gridSize - 1 - i) + 1));
            System.out.println(matrix[gridSize - 1 - i][0]);
        }
    }
}
