public class Constant implements Expression {
    private int constant;

    public Constant(int constant) {
        this.constant = constant;
    }

    @Override
    public int eval() {
        return constant;
    }
}