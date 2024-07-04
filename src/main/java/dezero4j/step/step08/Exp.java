package dezero4j.step.step08;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Exp extends AbstractFunction {
    @Override
    public double forward(double x) {
        double y = Math.exp(x);
        return y;
    }

    @Override
    public double backward(double gy) {
        double x = this.input.getData();
        double gx = Math.exp(x) * gy;
        return gx;
    }
}
