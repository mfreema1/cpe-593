public class PowerOperator extends Operator {

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
        return this;
    }

    @Override
    public Expression caseNeitherConstant(Variable a, Variable b) {
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
        return left + " " + right + " " + "^";
    }
}
