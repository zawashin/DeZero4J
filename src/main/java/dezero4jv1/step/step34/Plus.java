package dezero4jv1.step.step34;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Plus extends Function {

    public Plus() {
        numInputs = 2;
        numOutputs = 1;
    }
	@Override
    public double[][] forward(double[]... xs) {
        double[] x0 = xs[0];
        double[] x1 = xs[1];
        double[][] ys = new double[1][xs[0].length];
        for (int i = 0; i < x0.length; i++) {
            ys[0][i] = x0[i] + x1[i];
        }
        return ys;
    }

    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[2];
        gx[0] = gys[0];
        gx[1] = gys[0];
        return gx;
    }

}
