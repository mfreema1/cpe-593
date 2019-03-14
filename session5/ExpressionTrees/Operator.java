import java.util.*;
<<<<<<< HEAD

public abstract class Operator implements Expression{

    protected Expression right, left;

    public Operator(Expression right, Expression left) {
        this.right = right;
        this.left = left;
    }

    //attempt to evaluate an expression
    public abstract Expression caseBothConstants(Constant a, Constant b);

    public abstract Expression caseLeftConstant(Constant a, Variable b);

    public abstract Expression caseRightConstant(Variable a, Constant b);

    public abstract Expression caseNeitherConstant(Variable a, Variable b);

    //differentiate an expression with respect to c
    public abstract Expression diff(char c);

    //integrate an expression with respect to c
    public abstract Expression integrate(char c);

    public abstract String toString();

    //I don't know a better way to do this -- please forgive me
    @Override
    public Expression eval() {
        //try to solve the left and right subtrees
        left = left.eval();
        right = right.eval();

        if(left instanceof Constant && right instanceof Constant) {
            return caseBothConstants((Constant)left, (Constant)right);
        }
        else if(left instanceof Variable && right instanceof Constant) {
            return caseRightConstant((Variable)left, (Constant)right);
        }
        else if(left instanceof Constant && right instanceof Variable) {
            return caseLeftConstant((Constant)left, (Variable)right);
        }
        else if(left instanceof Variable && right instanceof Variable) {
            return caseNeitherConstant((Variable)left, (Variable)right);
        }
        
        //if one of them is still an operator, leave it
        return this;
=======
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
>>>>>>> 846027d580b6c0590f668ea30be387bce0738322
    }
}