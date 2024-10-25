package dezero4jv1.step.step12;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Plus extends Function {
    @Override
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
        if (gys.length != 1) {
            throw new IllegalArgumentException("Plus class requires exactly 1 gradient");
        }
        double[][] gxs = new double[inputs.length][];
        for (int i = 0; i < inputs.length; i++) {
            gxs[i] = new double[inputs[i].getData().length];
            System.arraycopy(gys[0], 0, gxs[i], 0, inputs[i].getData().length);
        }
        return gxs;
    }
}
