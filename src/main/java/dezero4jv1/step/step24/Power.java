package dezero4jv1.step.step24;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Power extends Function {
    private double index = 0;

    public void setIndex(double index) {
        this.index = index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public double[][] forward(double[][] xs) {
        double[] x0 = xs[0];
        double[][] ys = new double[1][xs[0].length];
        for (int i = 0; i < x0.length; i++) {
            ys[0][i] = Math.pow(x0[i], index);
        }
        return ys;
    }

    @Override
    public double[][] backward(double[][] gys) {
        double[][] gx = new double[1][];
        gx[0] = new double[inputs[0].getData().length];
        double[] xs = inputs[0].getData();
        for (int i = 0; i < inputs[0].getData().length; i++) {
            gx[0][i] = index * Math.pow(xs[i], index - 1) * gys[0][i];
        }
        return gx;
    }

}
