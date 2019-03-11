import java.util.*;

public class MyExpressionTree {

    private Stack<Expression> expressionStack;

    public MyExpressionTree() {
        this.expressionStack = new Stack<>();
    }

    //parse an RPN string and turn it into an expression tree
    public MyExpressionTree(String s) {
        this();

        StringTokenizer st = new StringTokenizer(s);
        while(st.hasMoreTokens()) {
            String token = st.nextToken();
            parseExpression(token);
        }
    }

    private void parseExpression(String token) {
        try{
            int constant = Integer.parseInt(token);
            Constant c = new Constant(constant);
            expressionStack.push(c);
        }
        //it must be an operator
        catch(NumberFormatException e) {
            //pop 2 off the stack, make right and left children
            Operator o = new Operator(expressionStack.pop(), expressionStack.pop(), token);
            expressionStack.push(o);
        }
    }

    //TODO: extra credit
    public void differentiate() {

    }

    //TODO: extra credit
    public void integrate() {

    }

    public int evaluate() {
        return expressionStack.pop().eval();
    }
}