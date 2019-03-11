import java.util.*;
import java.util.function.BiFunction;

public class Operator implements Expression {

    private Expression right, left;
    public String operator;

    public Operator(Expression right, Expression left, String operator) {
        this.right = right;
        this.left = left;
        this.operator = operator;
    }

    private static Map<String, BiFunction<Integer, Integer, Integer>> operators = new HashMap<>() {{
        put("+", Operator::add);
        put("-", Operator::subtract);
        put("*", Operator::multiply);
        put("/", Operator::divide);
        put("%", Operator::mod);
        put("^", Operator::power);
    }};

    private static int multiply(int a, int b) {
        return a * b;
    }

    private static int divide(int a, int b) {
        return a / b;
    }

    private static int add(int a, int b) {
        return a + b;
    }

    private static int subtract(int a, int b) {
        return a - b;
    }

    private static int mod(int a, int b) {
        return a % b;
    }

    private static int power(int a, int b) {
        return (int)Math.pow(a, b);
    }

    //apply this operator to the left and right nodes
    @Override
    public int eval() {
        return operators.get(operator).apply(left.eval(), right.eval());
    }
}