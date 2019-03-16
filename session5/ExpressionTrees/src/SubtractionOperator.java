public class SubtractionOperator extends Operator {

    private char symbol = '-';

    public SubtractionOperator(Expression right, Expression left) {
        super(right, left);
    }

    @Override
    public Expression caseBothConstants(Constant a, Constant b) {
        return new Constant(a.constant - b.constant);
    }

    @Override
    public Expression caseLeftConstant(Constant a, Variable b) {
        if(a.getVal() == 0)
            return b;
        return this;
    }

    @Override
    public Expression caseRightConstant(Variable a, Constant b) {
        return caseLeftConstant(b, a);
    }

    @Override
    public Expression caseNeitherConstant(Variable a, Variable b) {
        if(a.letter == b.letter) {
            return new Constant(0);
        }
        return this;
    }

    //anything minus zero is that thing
    @Override
    public Expression caseLeftOperator(Operator a, Constant b) {
        if(b.getVal() == 0)
            return a;
        return this;
    }

    //get it to just a negative if need be
    @Override
    public Expression caseRightOperator(Constant a, Operator b) {
        if(a.getVal() == 0)
            return new MultiplyOperator(b, new Constant(-1));
        return this;
    }

    //any expression minus itself is zero
    @Override
    public Expression caseBothOperators(Operator a, Operator b) {
        if(hasEqualSubtrees())
            return new Constant(0);
        return this;
    }

    @Override
    public Expression diff(char c) {
        return new SubtractionOperator(right.diff(c), left.diff(c));
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
