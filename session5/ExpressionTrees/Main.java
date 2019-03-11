public class Main {

    public static void main(String[] args) {
        MyExpressionTree et = new MyExpressionTree("3 2 * 5 6 * +");
        System.out.println(et.evaluate());
    }
}