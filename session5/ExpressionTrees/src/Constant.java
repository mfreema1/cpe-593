public class Constant implements Expression {
    public int constant;

    public Constant(int constant) {
        this.constant = constant;
    }
    
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
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof Constant))
            return false;
        Constant c = (Constant)o;
        return getVal() == c.getVal();
    }

    @Override
    public String toString() {
        return "" + constant;
    }
}