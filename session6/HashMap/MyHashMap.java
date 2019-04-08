import java.util.*;

public class MyHashMap {

    private Map<Integer,Integer> collisions = new HashMap<>();
    private static final Integer FNV_OFFSET = Integer.parseUnsignedInt("2166136261");
    private static final int FNV_PRIME = 16777619;
    private LinkedList<String>[] data;

    public MyHashMap() {
        this.data = new LinkedList[1000];
    }

    public MyHashMap(int capacity) {
        this.data  = new LinkedList[capacity];
    }

    public void insert(String s) {
        int index = hash(s);
        if(data[index] == null) {
            //nothing is there yet, make a new list
            LinkedList<String> newList = new LinkedList<>();
            newList.add(s);
            //append to the linked list
            data[index] = newList;
        }
        else {
            data[index].add(s);
        }
    }

    public boolean isWord(String p) {
        int index = hash(p);
        //if you leave out the <>, it becomes a raw type -- bad
        LinkedList<String> ll = data[index];
        if(ll == null)
            return false;
        else {
            for(String q : ll) {
                //this is probably really inefficient
                if(q.equals(p))
                    return true;
            }
            return false;
        }
    }

    private int hash(String s) {
        //FNV1-a hash
        int hash = FNV_OFFSET;
        byte[] bytes = s.getBytes();
        for(byte b : bytes) {
            hash = hash ^ b;
            hash = hash * FNV_PRIME;
        }
        return Integer.remainderUnsigned(hash, data.length);
    }

    public void printContents() {
        for(int i = 0; i < data.length; i++) {
            if(data[i].size() != 0)
                System.out.println(i + " " + data[i].size());
        }
    }
}