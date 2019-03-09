//build a trie to store words in a dictionary
//must load a list of words and check if they are all in the dictionary

//it's easy to have a binary tree -- it's hard to keep it balanced
//postorder is the easiest by far to parse

//constants, variables, and binary operators
//optimize the tree for the assignment

//you can also write an equation optimizer for a tree of size n to figure out the optimal equation

//contact Kruger for some of the tougher problems with trees

//sometimes a tree can be given a pointer to the parent if it needs to know where it came from
//read in in reverse polish notation, print back out in all three ways

//if we hash and miss, can we just hash the hash?  Would make it probabilistic I think, but that could be cool
public class JavaBinaryTree {
    private static class Node {
        int val;
        Node parent;
        Node left, right;

        public Node(int v, Node parent) {
            this.val = v;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }

        public void inorder() {
            if(left != null)
                left.inorder();
            System.out.print(val);
            if(right != null)
                right.inorder();
        }

        public void preorder() {
            System.out.print(val);
            if(left != null)
                left.preorder();
            if(right != null)
                right.preorder();
        }

        public void postorder() {
            if(left != null)
                left.postorder();
            if(right != null)
                right.postorder();
            System.out.print(val);
        }
    }

    private Node root;
    public JavaBinaryTree() {
        root = null;
    }

    //this just makes the tree, but does not balance it
    public add(int v) {
        if(root == null) {
            root = new Node(v, null);
        }
        Node p = head;
        if(v > p.val) {
            if(p.right == null) {
                p.right = new Node(v, p);
                return;
            }
            p = p.right;
        }
        else {
            if(p.left == null) {
                p.left = new Node(v, p);
                return;
            }
            p = p.left;
        }
    }

    public void inorder() {
        if(root != null)
            root.inorder();
    }

    public void preorder() {
        if(root != null)
            root.preorder();
    }

    public void postorder() {
        if(root != null)
            root.postorder();
    }
}