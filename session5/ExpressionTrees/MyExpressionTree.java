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
<<<<<<< HEAD
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
=======
            //pop 2 off the stack, make right and left children
            Operator o = new Operator(expressionStack.pop(), expressionStack.pop(), token);
            expressionStack.push(o);
>>>>>>> 846027d580b6c0590f668ea30be387bce0738322
        }
    }

    //TODO: extra credit
    public void differentiate() {

    }

    //TODO: extra credit
    public void integrate() {

    }

<<<<<<< HEAD
    public String evaluate() {
        return expressionStack.pop().eval().toString();
=======
    public int evaluate() {
        return expressionStack.pop().eval();
>>>>>>> 846027d580b6c0590f668ea30be387bce0738322
    }
}