package step.step07;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Exp extends Function {

    @Override
    protected double forward(double x) {
        double y = Math.exp(x);
        return y;
    }

    @Override
    protected double backward(double gy) {
        double x = this.input.getData();
        double gx = Math.exp(x) * gy;
        return gx;
    }
}
