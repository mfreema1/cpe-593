public class Main {

    public static void main(String[] args) {
        MyExpressionTree et = new MyExpressionTree("x y +");
        System.out.println(et.evaluate());
        System.out.println(et.differentiate('x'));
        System.out.println(et.integrate('x'));

        //TODO: extra credit -- perfectly hash all the keywords for C++11

        //Merkle Tree --> tree where each node has a hash of everything below it
        //could we use this to compare equals?  If we modify something, we just have to walk back up the tree to the root
        //and recompute the hash of each node along the way
    }
}