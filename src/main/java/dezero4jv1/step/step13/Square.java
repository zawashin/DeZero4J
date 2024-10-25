package dezero4jv1.step.step13;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {
    @Override
    public double[][] forward(double[][] xs) {
        double[][] ys = new double[1][1];
        ys[0][0] = xs[0][0] * xs[0][0];
        return ys;
    }

    @Override
    public double[][] backward(double[][] gys) {
        double[][] gx = new double[gys.length][1];
        for (int i = 0; i < gys.length; i++) {
            gx[i][0] = gys[i][0] * (inputs[i].getData())[0] * 2.0;
        }
        return gx;
    }
}
