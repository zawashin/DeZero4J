package dezero4jv1.step.step35;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Cos extends Function {

    public Cos() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public double[][] forward(double[]... xs) {
        double[] x0 = xs[0];
        int length = xs[0].length;
        double[][] ys = new double[numOutputs][length];
        for (int i = 0; i < length; i++) {
            ys[0][i] = Math.cos(x0[i]);
        }
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        Variable x = inputs[0];
        gx[0] = (x.sin().negative()).times(gys[0]);
        return gx;
    }
}
