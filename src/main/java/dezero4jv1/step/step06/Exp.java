package dezero4jv1.step.step06;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
class Exp extends Function {

    @Override
    public Variable forward(Variable input) {
        this.input = input;
        double x = input.data;
        double y = forward(x);
        return new Variable(y);
    }

    @Override
    protected double forward(double x) {
        return Math.exp(x);
    }

    @Override
    protected double backward(double gy) {
        double x = this.input.data;
        return Math.exp(x) * gy;
    }
}
