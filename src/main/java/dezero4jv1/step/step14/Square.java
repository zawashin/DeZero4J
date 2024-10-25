package dezero4jv1.step.step14;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {
    @Override
    public double[][] forward(double[][] xs) {
        double[] x = xs[0];
        double[][] ys = new double[1][x.length];
        for (int i = 0; i < xs[0].length; i++) {
            ys[0][i] = x[i] * x[i];
        }
        return ys;
    }

    @Override
    public double[][] backward(double[][] gys) {
        double[][] gx = new double[1][gys.length];
        for (int i = 0; i < gys.length; i++) {
            gx[0][i] = gys[0][i] * (inputs[0].getData())[i] * 2.0;
        }
        return gx;
    }
}
