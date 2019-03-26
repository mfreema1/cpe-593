public class Variable implements Expression {

    char letter;

    public Variable(char letter) {
        this.letter = letter;
    }

    /**
     * When we evaluate a variable, we just return itself
     */
    @Override
    public Expression eval() {
        return this;
    }

    /**
     * Differentiate a variable with respect to another
     */
    @Override
    public Expression diff(char d) {
        if(d == letter) {
            return new Constant(1);
        }
        return new Constant(0);
    }

    @Override
    public Expression integrate(char c) {
        if(letter == c) {
            return new DivisionOperator(new Constant(2), new PowerOperator(new Constant(2), this));
        }
        return new MultiplyOperator(new Variable(c), this);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof Variable))
            return false;
        Variable v = (Variable)o;
        return letter == v.letter;
    }

    @Override
    public String toString() {
        return "" + letter;
    }
}