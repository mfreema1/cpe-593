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
            //we have either a variable or an operator
            if(token.equals("*"))
                expressionStack.push(new MultiplyOperator(expressionStack.pop(), expressionStack.pop()));
            else if(token.equals("+"))
                expressionStack.push(new AdditionOperator(expressionStack.pop(), expressionStack.pop()));
            else if(token.equals("-"))
                expressionStack.push(new SubtractionOperator(expressionStack.pop(), expressionStack.pop()));
            else if(token.equals("/"))
                expressionStack.push(new DivisionOperator(expressionStack.pop(), expressionStack.pop()));
            else if(token.equals("%"))
                expressionStack.push(new ModuloOperator(expressionStack.pop(), expressionStack.pop()));
            else if(token.equals("^"))
                expressionStack.push(new PowerOperator(expressionStack.pop(), expressionStack.pop()));
            else
                expressionStack.push(new Variable(token.charAt(0)));
        }
    }

    public Expression differentiate(char c) {
        return expressionStack.peek().diff(c).eval();
    }

    //TODO: extra credit
    public Expression integrate(char c) {
        return expressionStack.peek().integrate(c).eval();
    }

    public Expression evaluate() {
        return expressionStack.peek().eval();
    }
}