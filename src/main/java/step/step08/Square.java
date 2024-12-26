package step.step08;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {
    @Override
    public double forward(double x) {
        double y = Math.pow(x, 2);
        return y;
    }

    @Override
    public double backward(double gy) {
        double x = this.input.getData();
        double gx = 2 * x * gy;
        return gx;
    }
}
