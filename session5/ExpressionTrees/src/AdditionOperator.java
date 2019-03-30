public class AdditionOperator extends Operator {

    private char symbol = '+';

    public AdditionOperator(Expression right, Expression left) {
        super(right, left);
    }

    @Override
    public Expression caseBothConstants(Constant a, Constant b) {
        return new Constant(a.constant + b.constant);
    }

    @Override
    public Expression caseLeftConstant(Constant a, Variable b) {
        if(a.constant == 0)
            return b;
        return this;
    }

    @Override
    public Expression caseRightConstant(Variable a, Constant b) {
        return caseLeftConstant(b, a);
    }

    @Override
    public Expression caseNeitherConstant(Variable a, Variable b) {
        if(a.letter == b.letter)
            return new MultiplyOperator(a, new Constant(2));
        return this;
    }

    @Override
    public Expression caseLeftOperator(Operator a, Constant b) {
        if(b.getVal() == 0)
            return a;
        return this;
    }

    //addition is reflexive
    @Override
    public Expression caseRightOperator(Constant a, Operator b) {
        return caseLeftOperator(b, a);
    }

    //just multiply by 2
    @Override
    public Expression caseBothOperators(Operator a, Operator b) {
        if(hasEqualSubtrees())
            return new MultiplyOperator(a, new Constant(2));
        return this;
    }

    @Override
    public Expression diff(char c) {
        return new AdditionOperator(right.diff(c), left.diff(c));
    }

    @Override
    public Expression integrate(char c) {
        return new AdditionOperator(right.integrate(c), left.integrate(c));
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
