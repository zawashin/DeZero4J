package dezero4jv1.step.step10;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {
    @Override
    public double[] forward(double[] x) {
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = Math.pow(x[i], 2);
        }
        return y;
    }

    @Override
    public double[] backward(double[] gy) {
        double[] x = this.getInput().getData();
        double[] gx = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            gx[i] = 2 * x[i] * gy[i];
        }
        return gx;
    }
}
