public class AdditionOperator extends Operator {

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
    public Expression diff(char c) {
        return new AdditionOperator(right.diff(c), left.diff(c));
    }

    @Override
    public Expression integrate(char c) {
        return null;
    }

    @Override
    public String toString() {
        return left + " " + right + " " + "+";
    }
}
