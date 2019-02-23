//most lists are empty -- keep a low overhead
public class LinkedList {

    //a static inner class removes the reference to the parent
    private static class Node {
        public Node next;
        public int val;
    }

    private Node head;
    public LinkedList() {
        head = null;
    }

    //O(1), this is a smart way to do it, no special case
    public void addStart(int val) {
        Node temp = new Node();
        temp.val = v;
        temp.next = head;
        head = temp;
    }

    //O(n) in total
    public void addEnd(int val) {
        Node temp = new Node();
        temp.val = v;
        temp.next = null;
        if(head == null) {
            head = temp;
            return;
        }
        //guaranteed to have at least 1 element
        Node p;
        for(p = head; p.next != null; p = p.next); //move it to the end
        p.next = temp;
    }

    //O(n), but you could also put it on the class to distribute the work over
    //all the add and reduce call.  Then this would be O(1)
    public int size() {
        int count = 0;
        for(Node p = head; p != null; p = p.next)
            count++;
        return count;
    }

    //there are certain operations that cannot be corrected and will only slow us down,
    //such as preventing the access of an item not in the list
    public int get(int pos) { //O(pos) = O(n)
        Node p = head;
        for(int i = 0; i < pos; i++) {
            p = p.next;
        }
        return p.val;
    }

    //O(1)
    //once you have this API where you are returning an integer, there is no sensible way to
    //handle the error of having an empty list, let them crash
    public int removeStart() {
        Node temp = head;
        head = head.next;
        return temp.val;
    }

    //using .next.next is slow because each time we jump, we have to access two ahead, even on ones we've checked
    //again, if you have no elements, you have no business coming here
    public int removeEnd() {
        Node p = head, q = p.next;
        if(q == null) { //only have one element in the list
            int last = head.val;
            head = null;
            return last;
        }
        //invariant: at least 2 elements
        for(; q.next != null; p = q, q = q.next) {
            //q is on the last one, p is on the second to last
        }
        p.next = null;
        return q.val; //keep in mind we have a memory leak if we are not in a garbage collected language
    }

    //StringBuffer is a thread safe version of StringBuilder, but we are the only ones with our builder, so only one thread has it --
    //thus we don't care about being thread safe
    public String toString() {
        StringBuilder b = new StringBuilder(size * 6);
        for(Node p = head; p != null; p = p.next)
            b.append(p.val).append(' ');
        return b.toString();
    }
}