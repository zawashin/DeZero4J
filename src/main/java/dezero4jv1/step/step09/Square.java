package dezero4jv1.step.step09;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {
    @Override
    public double[] forward(double[] x) {
        double[] y = new double[x.length];
        for(int i = 0; i < x.length; i++) {
            y[i] = x[i] * x[i];
        }
        return y;
    }

    @Override
    public double[] backward(double[] gy) {
        double[] x = getInput().getData();
        //double[] gx = x.maptimes(2).ebetimes(gy);
        double[] gx = new double[x.length];
        for(int i = 0; i < x.length; i++) {
            gx[i] = 2.0 * x[i] * gy[i];
        }
        return gx;
    }
}
