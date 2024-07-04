package dezero4j.step.step06;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
class Square extends AbstractFunction {
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
        double y = x * x;
        return y;
    }

    @Override
    public double backward(double gy) {
        double x = this.input.data;
        double gx = 2 * x * gy;
        return gx;
    }
}
