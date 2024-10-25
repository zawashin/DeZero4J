package dezero4jv1.step.step09;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Exp extends Function {
    @Override
    public double[] forward(double[] x) {
        double[] y = new double[x.length];
        for(int i = 0; i < y.length; i++) {
            y[i] = Math.exp(x[i]);
        }
        return y;
    }

    @Override
    public double[] backward(double[] gy) {
        double[] x = getInput().getData();
        double[] gx = new double[x.length];
        for(int i = 0; i < gx.length; i++) {
            gx[i] = Math.exp(x[i]) * gy[i];
        }
        return gx;
    }
}
