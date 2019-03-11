import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        DictTrie trie = new DictTrie();
        trie.loadFile("example.txt");
        System.out.println(trie.isInDict("aardvark"));
        System.out.println(trie.isInDict("actually"));
        System.out.println(trie.isValidPrefix("carr"));
        System.out.println(trie.isValidPrefix("foo"));
    }
}