public class PowerOperator extends Operator {

    private char symbol = '^';

    public PowerOperator(Expression right, Expression left) {
        super(right, left);
    }
    
    @Override
    public Expression caseBothConstants(Constant a, Constant b) {
        return new Constant((int)Math.pow(a.constant, b.constant));
    }

    //anything to the power of 0 is 1
    @Override
    public Expression caseLeftConstant(Constant a, Variable b) {
        if(a.getVal() == 0 || a.getVal() == 1)
            return new Constant(a.getVal());
        return this;
    }

    @Override
    public Expression caseRightConstant(Variable a, Constant b) {
        if(b.getVal() == 0)
            return new Constant(1);
        if(b.getVal() == 1)
            return a;
        return this;
    }

    @Override
    public Expression caseNeitherConstant(Variable a, Variable b) {
        return this;
    }

    //anything to the power of 0 is 1
    //anything to the 1 is itself
    @Override
    public Expression caseLeftOperator(Operator a, Constant b) {
        if(b.getVal() == 0)
            return new Constant(1);
        if(b.getVal() == 1)
            return a;
        return this;
    }

    //0 to anything is 0, 1 to anything is 1
    @Override
    public Expression caseRightOperator(Constant a, Operator b) {
        if(a.getVal() == 0 || a.getVal() == 1)
            return new Constant(a.getVal());
        return this;
    }

    //no good simplification
    @Override
    public Expression caseBothOperators(Operator a, Operator b) {
        return this;
    }

    @Override
    public Expression diff(char c) {
        return new MultiplyOperator(left.diff(c), new MultiplyOperator(new PowerOperator(new SubtractionOperator(new Constant(1), right), left), right));
    }

    @Override
    public Expression integrate(char c) {
        return null;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
