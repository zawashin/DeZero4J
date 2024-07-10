package dezero4j.step.step07;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
class Square extends Function {
    @Override
    protected double forward(double x) {
        double y = Math.pow(x, 2);
        return y;
    }

    @Override
    protected double backward(double gy) {
        double x = this.input.getData();
        double gx = 2 * x * gy;
        return gx;
    }
}
