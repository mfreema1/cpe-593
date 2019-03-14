public class DivisionOperator extends Operator {

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
    public String toString() {
        return left + " " + right + " " + "/";
    }
}
