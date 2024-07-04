package dezero4j.step.step06;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
class Exp extends AbstractFunction {
    @Override
    public Variable forward(Variable input) {
        double x = input.data;
        double y = forward(x);
        Variable output = new Variable(y);
        this.input = input;
        return output;
    }

    @Override
    public double forward(double x) {
        double y = Math.exp(x);
        return y;
    }

    @Override
    public double backward(double gy) {
        double x = this.input.data;
        double gx = Math.exp(x) * gy;
        return gx;
    }
}
