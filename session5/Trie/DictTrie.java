import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DictTrie {
    
    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    
    private static class Node {
        boolean isWord;
        Node[] connections;

        public Node() {
            this.isWord = false;
            this.connections = new Node[LETTERS.length()];
        }
    }

    private Node root;

    public DictTrie() {
        this.root = new Node();
    }

    public boolean isValidPrefix(String prefix) {
        Node temp = root;
        for(char c : prefix.toCharArray()) {
            if(temp == null)
                return false;
            temp = temp.connections[LETTERS.indexOf(c)];
        }
        return true;
    }

    public boolean isInDict(String word) {
        Node temp = root;
        for(char c : word.toCharArray()) {
            if(temp == null)
                return false;
            temp = temp.connections[LETTERS.indexOf(c)];
        }
        return temp.isWord;
    }

    //TODO: Why does this not work?
    // public void add(String word) {
    //     Node temp = root;
    //     for(char c : word.toCharArray()) {
    //         Node next = temp.connections[LETTERS.indexOf(c)];
    //         if(next == null)
    //             next = new Node();
    //         temp = next;
    //     }
    //     temp.isWord = true;
    // }

    public void add(String word) {
        Node temp = root;
        for(char c : word.toCharArray()) {
            int i = LETTERS.indexOf(c);
            if(temp.connections[i] == null)
                temp.connections[i] = new Node();
            temp = temp.connections[i];
        }
        temp.isWord = true;
    }

    public void loadFile(String path) {
        try {
            File f = new File(path);
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                add(in.next());
            }
            in.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Could not find file");
        }
    }
}