import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    //TODO: load in the file from Prof. Kruger and print whether
    //or not it is in the trie
    public static void main(String[] args) {
        String dictionaryPath = "dict.txt";
        String inputPath = "hw5.txt";

        DictTrie trie = new DictTrie();
        trie.loadFile(dictionaryPath);
        try {
            Scanner in = new Scanner(new File(inputPath));
            while(in.hasNext()) {
                System.out.println(trie.isInDict(in.next()));
            }
            in.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Could not load input at " + inputPath);
        }
    }
}