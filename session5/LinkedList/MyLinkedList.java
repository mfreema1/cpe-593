import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class MyLinkedList implements Iterable{

    private static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
            this.next = null;
        }
    }

    Node head, tail;

    //internal method to append, knowing that head is not null
    private void prepend(Node temp) {
        temp.next = head;
        head = temp;
    }

    private void append(Node temp) {
        tail.next = temp;
        tail = temp;
    }

    //O(1)
    public void addFront(int val) {
        Node temp = new Node(val);
        //special case: empty
        if(head == null)
            head = tail = temp;
        else
            prepend(temp);
    }

    //O(1)
    public void addBack(int val) {
        Node temp = new Node(val);
        //special case: empty
        if(head == null)
            head = tail = temp;
        else
            append(temp);
    }

    public void addBack(int start, int step, int end) {
        if(head == null) {
            head = tail = new Node(start);
            start += end;
        }
        for(; start <= end; start += step) {
            append(new Node(start));
        }
    }

    //O(1)
    public void removeFront() {
        //special case: only one
        if(head.next == null) {
            head = tail = null;
        }
        else {
            //leave it for the garbage collector
            head = head.next;
        }
    }

    //O(len)
    public void removeBack() {
        if(head.next == null) {
            head = tail = null;
        }
        else {
            Node p = head;
            while(p.next != tail)
                p = p.next;
            p.next = null;
            tail = p;
        }
    }

    public void removeBack(int n) {
        //make a stagger
        Node lead, follow;
        lead = follow =  head;
        //advance the leader n positions
        for(int i = 0; i < n; i++, lead = lead.next)
            ;
        while(lead.next != null) {
            lead = lead.next;
            follow = follow.next;
        }
        //lead is now on the end of the list, deconstruct from follow (in Java, just cast it into the abyss)
        follow.next = null; //*snip*
    }

    public void addFront(int start, int step, int end) {
        if(head == null) {
            head = tail = new Node(start);
            start += step;
        }
        for(; start <= end; start += step) {
            prepend(new Node(start));
        }
    }

    public void removeFront(int n) {
        while(n > 0) {
            head = head.next;
            n--;
        }
    }

    //notice it has no reference whatsoever to the Node
    public void output() {
        StringJoiner sj = new StringJoiner(",");
        Iterator iterator = iterator();
        while(iterator.hasNext()) {
            sj.add(iterator.next().toString());
        }
        System.out.println(sj.toString());
    }

    //we do not let them know what is actually happening under the covers
    public Iterator iterator() {
        return new Iterator<Integer>() {
            
            private Node currentNode = head;

            //TODO: feel like we should have somebody pointing to head
            //at all times internally.  The current logic on these two
            //methods feels incorrect
            public boolean hasNext() {
                return currentNode != null;
            }

            public Integer next() {
                int val = currentNode.val;
                currentNode = currentNode.next;
                return val;
            }
        };
    }

    //let's assume that the file is always valid
    //I know it's messy and not the best way, but I just want 
    //something quick to drive the program
    public void loadFile(String path) {
        try {
            File f = new File(path);
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                String line = in.nextLine();
                if(!line.equals("OUTPUT")) {
                    StringTokenizer st = new StringTokenizer(line);
                    String first = st.nextToken();
                    String second = st.nextToken();
                    //adds all have the colon separated args
                    if(first.startsWith("ADD")) {
                        StringTokenizer st2 = new StringTokenizer(second);
                        int start = Integer.parseInt(st2.nextToken(":"));
                        int step = Integer.parseInt(st2.nextToken(":"));
                        int end = Integer.parseInt(st2.nextToken(":"));
                        if(first.equals("ADD_FRONT"))
                            addFront(start, step, end);
                        else if(first.equals("ADD_BACK"))
                            addBack(start, step, end);
                    }
                    //remove all have only one arg
                    else if(first.equals("REMOVE_BACK")) {
                        removeBack(Integer.parseInt(second));
                    }
                    else if(first.equals("REMOVE_FRONT")) {
                        removeFront(Integer.parseInt(second));
                    }
                }
                else {
                    output();
                }
            }
            in.close();
        }   
        catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}