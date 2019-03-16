public class DivisionOperator extends Operator {

    private char symbol = '/';

    public DivisionOperator(Expression right, Expression left) {
        super(right, left);
    }

    @Override
    public Expression caseBothConstants(Constant a, Constant b) {
        return new Constant(a.getVal() / b.getVal());
    }

    //can't do anything with this
    @Override
    public Expression caseLeftConstant(Constant a, Variable b) {
        return this;
    }

    @Override
    public Expression caseRightConstant(Variable a, Constant b) {
        return this;
    }

    //can only do this if they are of the same variable and
    //we guarantee the denominator is not zero
    @Override
    public Expression caseNeitherConstant(Variable a, Variable b) {
        if(a.letter == b.letter)
            return new Constant(1);
        return this;
    }

    @Override
    public Expression caseLeftOperator(Operator a, Constant b) {
        if(b.getVal() == 1)
            return a;
        return this;
    }

    @Override
    public Expression caseRightOperator(Constant a, Operator b) {
        if(a.getVal() == 0)
            return new Constant(0);
        if(a.getVal() == 1)
            return new PowerOperator(new Constant(-1), b);
        return this;
    }

    @Override
    public Expression caseBothOperators(Operator a, Operator b) {
        if(hasEqualSubtrees())
            return new Constant(1);
        return this;
    }

    //division rule
    @Override
    public Expression diff(char c) {
        return new DivisionOperator(new PowerOperator(new Constant(2), right), new SubtractionOperator(new MultiplyOperator(right.diff(c), left), new MultiplyOperator(left.diff(c), right)));
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
