public class MultiplyOperator extends Operator {

    private char symbol = '*';

    public MultiplyOperator(Expression right, Expression left) {
        super(right, left);
    }

    @Override
    public Expression caseBothConstants(Constant a, Constant b) {
        return new Constant(a.constant * b.constant);
    }

    //we cannot evaluate this, just leave it
    @Override
    public Expression caseLeftConstant(Constant a, Variable b) {
        if(a.getVal() == 0)
            return new Constant(0);
        if(a.getVal() == 1)
            return new Constant(1);
        return this;
    }

    @Override
    public Expression caseRightConstant(Variable a, Constant b) {
        return caseLeftConstant(b, a);
    }

    //can only do it if we can make a power out of it
    @Override
    public Expression caseNeitherConstant(Variable a, Variable b) {
        if(a.letter == b.letter)
            return new PowerOperator(new Constant(2), a);
        return this;
    }

    @Override
    public Expression caseLeftOperator(Operator a, Constant b) {
        if(b.getVal() == 0)
            return new Constant(0);
        if(b.getVal() == 1)
            return a;
        return this;
    }

    //multiplication is commutative
    @Override
    public Expression caseRightOperator(Constant a, Operator b) {
        return caseLeftOperator(b, a);
    }

    //any expression times itself is the square of it
    @Override
    public Expression caseBothOperators(Operator a, Operator b) {
        if(hasEqualSubtrees())
            return new PowerOperator(new Constant(2), a);
        return this;
    }

    //multiplication rule
    @Override
    public Expression diff(char c) {
        return new AdditionOperator(new MultiplyOperator(left.diff(c), right), new MultiplyOperator(right.diff(c), left));
    }

    /**
     * This is troublesome... how can we do this without examining all of the cases again?  It does not seem as cut
     * and dry as the differentiation
     */
    @Override
    public Expression integrate(char c) {
        return null;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
