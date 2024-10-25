package dezero4jv1.step.step34;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Times extends Function {

    public Times() {
        numInputs = 2;
        numOutputs = 1;
    }

	@Override
    public double[][] forward(double[]... xs) {
        double[] x0 = xs[0];
        double[] x1 = xs[1];
        double[][] ys = new double[numOutputs][xs[0].length];
        for (int i = 0; i < x0.length; i++) {
            ys[0][i] = x0[i] * x1[i];
        }
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        Variable[] xs = new Variable[numInputs];
        xs[0] = inputs[0];
        xs[1] = inputs[1];
        gx[0] = gys[0].times(xs[1]);
        gx[1] = gys[0].times(xs[0]);
        return gx;
    }
}
