package dezero4jv1.step.step11;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Plus extends Function {
    public double[][] forward(double[][] xs) {
        double[] x0 = xs[0];
        double[] x1 = xs[1];
        double[] y = new double[x0.length];
        for (int i = 0; i < x0.length; i++) {
            y[i] = x0[i] + x1[i];
        }
        return new double[][]{y};
    }

    @Override
    public double[][] backward(double[][] gys) {
        return new double[0][];
    }
}
