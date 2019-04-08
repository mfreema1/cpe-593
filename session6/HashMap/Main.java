import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        MyHashMap hashmap = new MyHashMap();

        Scanner in = new Scanner(new File("dict.txt"));
        while(in.hasNext()) {
            hashmap.insert(in.next());
        }
        in.close();

        Scanner check = new Scanner(new File("words.txt"));
        while(check.hasNext()) {
            System.out.println(hashmap.isWord(check.next()));
        }
        check.close();

        hashmap.printContents();
    }
}