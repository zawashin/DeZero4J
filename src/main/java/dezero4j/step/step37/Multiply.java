package dezero4j.step.step37;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Multiply extends Function {

    public Multiply() {
        numInputs = 2;
        numOutputs = 1;
    }

    public Tensor[] forward(Tensor... xs) {
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = x0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = x0[i] * x1[i];
        }
        Tensor[] ys = new Tensor[1];
        ys[0] = new Tensor(values);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        Variable[] xs = new Variable[numInputs];
        xs[0] = inputs[0];
        xs[1] = inputs[1];
        gx[0] = gys[0].multiply(xs[1]);
        gx[1] = gys[0].multiply(xs[0]);
        return gx;
    }
}
