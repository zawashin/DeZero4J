package dezero4jv1.step.step36;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Tanh extends Function {

    public Tanh() {
        numInputs = 1;
        numOutputs = 1;
    }


    @Override
    public double[][] forward(double[]... xs) {
        double[] x0 = xs[0];
        double[][] ys = new double[1][xs[0].length];
        for (int i = 0; i < x0.length; i++) {
            ys[0][i] = Math.tanh(x0[i]);
        }
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        Variable gy =  gys[0];
        Variable y = outputs[0];
        int length = gys[0].length;
        gx[0] = gy.times(param(length, 1).minus(y.pow(2)));

        return gx;
    }
}
