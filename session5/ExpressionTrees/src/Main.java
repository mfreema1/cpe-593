import java.util.*;

public class Main {

    public static void main(String[] args) {
        MyExpressionTree et = new MyExpressionTree("x 2 ^ y +");
        // System.out.println(et.evaluate());
        String once = et.differentiate('x').toString();
        System.out.println(once);
        String twice = new MyExpressionTree(once).evaluate().toString();
        System.out.println(twice);

        //TODO: extra credit -- perfectly hash all lthe keywords for C++11

        //Merkle Tree --> tree where each node has a hash of everything below it
        //could we use this to compare equals?  If we modify something, we just have to walk back up the tree to the root
        //and recompute the hash of each node along the way
    }
}