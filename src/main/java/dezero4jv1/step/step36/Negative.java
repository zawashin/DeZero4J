package dezero4jv1.step.step36;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Negative extends Function {

    public Negative() {
        numInputs = 1;
        numOutputs = 1;
    }
	@Override
    public double[][] forward(double[]... xs) {
        double[] x0 = xs[0];
        double[][] ys = new double[1][xs[0].length];
        for (int i = 0; i < x0.length; i++) {
            ys[0][i] = -x0[i];
        }
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        gx[0] = gys[0].negative();
        return gx;
    }
}
