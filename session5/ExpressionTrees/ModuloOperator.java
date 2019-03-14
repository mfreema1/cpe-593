public class ModuloOperator extends Operator {

    public ModuloOperator(Expression right, Expression left) {
        super(right, left);
    }

    @Override
    public Expression caseBothConstants(Constant a, Constant b) {
        return new Constant(a.constant % b.constant);
    }

    @Override
    public Expression caseLeftConstant(Constant a, Variable b) {
        return this;
    }

    @Override
    public Expression caseRightConstant(Variable a, Constant b) {
        return caseLeftConstant(b, a);
    }

    //this is not allowed to happen
    @Override
    public Expression caseNeitherConstant(Variable a, Variable b) {
        return null;
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
        return left + " " + right + " " + "%";
    }
}
