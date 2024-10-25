package dezero4jv1.step.step35;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Div extends Function {

    public Div() {
        numInputs = 2;
        numOutputs = 1;
    }

    @Override
    public double[][] forward(double[]... xs) {
        int length = xs[0].length;
        double[] x0 = xs[0];
        double[] x1 = xs[1];
        double[][] ys = new double[numInputs][length];
        for (int i = 0; i < length; i++) {
            ys[0][i] = x0[i] / x1[i];
        }
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        Variable[] xs = new Variable[numInputs];
        xs[0] = inputs[0];
        xs[1] = inputs[1];
        gx[0] = gys[0].div(xs[0]);
        gx[1] = gys[0].negative().times(xs[0].div(xs[1].pow(2)));
        return gx;
    }
}
