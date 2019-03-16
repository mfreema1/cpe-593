import java.util.*;

public abstract class Operator implements Expression{

    protected Expression right, left;

    public Operator(Expression right, Expression left) {
        this.right = right;
        this.left = left;
    }

    //attempt to evaluate a simple expression
    public abstract Expression caseBothConstants(Constant a, Constant b);

    public abstract Expression caseLeftConstant(Constant a, Variable b);

    public abstract Expression caseRightConstant(Variable a, Constant b);

    public abstract Expression caseNeitherConstant(Variable a, Variable b);

    //adding in complex simplification
    public abstract Expression caseLeftOperator(Operator a, Constant b);

    public abstract Expression caseRightOperator(Constant a, Operator b);

    public abstract Expression caseBothOperators(Operator a, Operator b);

    //can't think of a way to add in Operator and Variable simplification yet

    //differentiate an expression with respect to c
    public abstract Expression diff(char c);

    //integrate an expression with respect to c
    public abstract Expression integrate(char c);

    public abstract char getSymbol();

    //utility function to help simplify operators like subtraction, division, and addition which all have
    //trivial solutions if the trees are the same
    public boolean hasEqualSubtrees() {
        boolean treesEqual = left.equals(right);
        System.out.println(treesEqual);
        return treesEqual;
//        return left.equals(right);
    }

    //allow for comparison of operators to other expressions
    @Override
    public boolean equals(Object expression) {
        if(expression == this)
            return true;
        if(!(expression instanceof Operator))
            return false;
        Operator operator = (Operator)expression;
        return getSymbol() == operator.getSymbol() && operator.left.equals(left) && operator.right.equals(right);
    }

    //I don't know a better way to do this -- please forgive me
    @Override
    public Expression eval() {
        //try to solve the left and right subtrees
        left = left.eval();
        right = right.eval();
        System.out.println(left.getClass());
        System.out.println(right.getClass());

        if(left instanceof Constant && right instanceof Constant) {
            System.out.println("caseBothConst");
            return caseBothConstants((Constant)left, (Constant)right);
        }
        else if(left instanceof Variable && right instanceof Constant) {
            System.out.println("caseRightConst");            
            return caseRightConstant((Variable)left, (Constant)right);
        }
        else if(left instanceof Constant && right instanceof Variable) {
            System.out.println("caseLeftConst");
            return caseLeftConstant((Constant)left, (Variable)right);
        }
        else if(left instanceof Variable && right instanceof Variable) {
            System.out.println("caseNeitherConst");
            return caseNeitherConstant((Variable)left, (Variable)right);
        }
        else if(left instanceof Operator && right instanceof Constant) {
            System.out.println("caseLeftOps");
            return caseLeftOperator((Operator)left, (Constant)right);
        }
        else if(left instanceof Constant && right instanceof Operator) {
            System.out.println("caseRightOps");
            return caseRightOperator((Constant)left, (Operator)right);
        }
        else if(left instanceof Operator && right instanceof Operator) {
            System.out.println("caseBothOps");
            return caseBothOperators((Operator)left, (Operator)right);
        }
        
        //we don't yet handle the case where there is an operator and a variable
        return this;
    }

    //reverse polish notation
    public String toString() {
        return left + " " + right + " " + getSymbol();
    }
}