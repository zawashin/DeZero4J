package dezero4j.step.step12;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Plus extends AbstractFunction {
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
            throw new IllegalArgumentException("zawashin.dezero4j.step162.Add function requires exactly 1 gradient");
        }
        double[][] gxs = new double[inputs.length][];
        for (int i = 0; i < inputs.length; i++) {
            gxs[i] = new double[inputs[i].getData().length];
            for (int j = 0; j < inputs[i].getData().length; j++) {
                gxs[i][j] = gys[0][j];
            }
        }
        return gxs;
        //return new double[0][];
    }
}
