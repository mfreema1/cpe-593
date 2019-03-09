//implement rotate red and rotate right for points
public class RedBlackTree {
    //1. each node is either red or black
    //2. the root is black (arbitrary)
    //3. all leaves are the same color as the root
    //4. if a node is red, its children are black

    private static class Node {
        int val;
        boolean red;
        Node parent;
        Node left, right;

        public Node(int v, Node parent) {
            this.val = v;
            this.parent = parent;
            this.left = null;
            this.right = null;
            this.red = true;
        }
    }

    private node root;
    public RedBlackTree() {
        root = null;
    }
    
    //red black tree is extra credit
    public add(int v) {
        if(root == null) {
            root = new Node(v, null);
        }
        Node p = head;
        if(v > p.val) {
            if(p.right == null) {
                p.right = new Node(v, p);
                fixTree(p.right);
                return;
            }
            p = p.right;
        }
        else {
            if(p.left == null) {
                p.left = new Node(v, p);
                fixTree(p.left);
                return;
            }
            p = p.left;
        }
    }

    public static Node parent(Node n) {
        if(n == null)
            return null;
        return n.parent;
    }

    public static Node uncle(Node n) {
        Node p = parent(n);
        if(p == null)
            return null;
        Node g = parent(p);
        return (p == g.right) ? g.left : g.right;
    }
    public void fixTree(Node n) {
        Node p = parent(n);
        Node u = uncle(n);
        Node g = parent(p);
        if(p == null) {
            n.red = false;
            insert_case1(n);
        }
        else if(p.red == false) {
            return;
        }
        else if(u != null && uncle.red) {
            //insert_case3(n);
            p.red = false;
            u.red = false;
            g.red = true;
            fixTree(g);
        }
        else {
            if(n == p.right && p == g.left) {
                rotate_left(p);
                n = n.left;
            }
            else if(n == p.left && p == g.right) {
                rotate_right(p);
                n = n.right;
            }
            insert_case4step2(n);
        }
    }
}