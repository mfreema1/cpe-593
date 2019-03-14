public class Main {

    public static void main(String[] args) {
        MyExpressionTree et = new MyExpressionTree("x x - a b + *");
        System.out.println(et.evaluate());
    }
}