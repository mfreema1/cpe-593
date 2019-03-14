public class Constant implements Expression {
<<<<<<< HEAD
    public int constant;
=======
    private int constant;
>>>>>>> 846027d580b6c0590f668ea30be387bce0738322

    public Constant(int constant) {
        this.constant = constant;
    }
<<<<<<< HEAD
    
    public int getVal() {
        return constant;
    }

    @Override
    public Expression eval() {
        return this;
    }

    /**
     * No matter what, a constant becomes zero when differentiated
     */
    @Override
    public Expression diff(char c) {
        return new Constant(0);
    }

    /**
     * Whenever we integrate a constant, we must simply tack the variable onto the expression
     * @example: 15 with respect to x => 15x
     */
    @Override
    public Expression integrate(char c) {
        return new MultiplyOperator(this, new Variable(c));
    }

    @Override
    public String toString() {
        return "" + constant;
=======

    @Override
    public int eval() {
        return constant;
>>>>>>> 846027d580b6c0590f668ea30be387bce0738322
    }
}