import java.util.*;

public class Main {

    public static void main(String[] args) {
        MyExpressionTree et = new MyExpressionTree("x x - a b + *");
        // System.out.println(et.evaluate());
        System.out.println(et.differentiate('x'));
    }
}