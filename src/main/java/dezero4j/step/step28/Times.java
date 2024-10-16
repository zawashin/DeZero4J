package dezero4j.step.step28;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Times extends Function {
	@Override
    public double[][] forward(double[][] xs) {
        double[] x0 = xs[0];
        double[] x1 = xs[1];
        double[][] ys = new double[1][xs[0].length];
        for (int i = 0; i < x0.length; i++) {
            ys[0][i] = x0[i] * x1[i];
        }
        return ys;
    }

    @Override
    public double[][] backward(double[][] gys) {
        double[][] gx = new double[2][];
        gx[0] = new double[inputs[0].getData().length];
        gx[1] = new double[inputs[1].getData().length];
        double[][] xs = new double[2][];
        xs[0] = inputs[0].getData();
        xs[1] = inputs[1].getData();
        for (int i = 0; i < inputs[0].getData().length; i++) {
            gx[0][i] = gys[0][i] * xs[1][i];
            gx[1][i] = gys[0][i] * xs[0][i];
        }
        return gx;
    }

}
