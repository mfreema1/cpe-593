public class MultiplyOperator extends Operator {

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
    public Expression diff(char c) {
        return null;
    }

    @Override
    public Expression integrate(char c) {
        return null;
    }

    @Override
    public String toString() {
        return left + " " + right + " " + "*";
    }
}
