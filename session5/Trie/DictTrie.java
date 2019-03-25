import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DictTrie {
        
    private static class Node {
        boolean isWord;
        Node[] connections;

        public Node() {
            this.isWord = false;
            this.connections = new Node[26];
        }
    }

    private Node root;

    public DictTrie() {
        this.root = new Node();
    }

    public boolean isValidPrefix(String prefix) {
        Node temp = root;
        for(int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            int nextIndex = c - 'a';
            if(temp.connections[nextIndex] == null)
                return false;
            temp = temp.connections[nextIndex];
        }
        return true;
    }

    public boolean isInDict(String word) {
        Node temp = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int nextIndex = c - 'a';
            if(temp.connections[nextIndex] == null)
                return false;
            temp = temp.connections[nextIndex];
        }
        return temp.isWord;
    }

    public void add(String word) {
        Node temp = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int nextIndex = c - 'a';
            if(temp.connections[nextIndex] == null)
                temp.connections[nextIndex] = new Node();
            temp = temp.connections[nextIndex];
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
            System.out.println("Could not load dictionary at " + path);
        }
    }
}