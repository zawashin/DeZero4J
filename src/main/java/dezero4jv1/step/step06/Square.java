package dezero4jv1.step.step06;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
class Square extends Function {

    @Override
    public Variable forward(Variable input) {
        double x = input.data;
        double y = forward(x);
        Variable output = new Variable(y);
        this.input = input;
        return output;
    }

    @Override
    protected double forward(double x) {
        return x * x;
    }

    @Override
    protected double backward(double gy) {
        double x = this.input.data;
        return 2 * x * gy;
    }
}
