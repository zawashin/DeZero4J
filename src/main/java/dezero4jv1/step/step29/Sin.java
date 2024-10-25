package dezero4jv1.step.step29;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sin extends Function {
    private final double index = 0;

    @Override
    public double[][] forward(double[][] xs) {
        double[] x0 = xs[0];
        double[][] ys = new double[1][xs[0].length];
        for (int i = 0; i < x0.length; i++) {
            ys[0][i] = Math.sin(x0[i]);
        }
        return ys;
    }

    @Override
    public double[][] backward(double[][] gys) {
        double[][] gx = new double[1][];
        gx[0] = new double[inputs[0].getData().length];
        double[] xs = inputs[0].getData();
        for (int i = 0; i < inputs[0].getData().length; i++) {
            gx[0][i] = Math.cos(xs[i]) * gys[0][i];
        }
        return gx;
    }

}
