package dezero4jv1.step.step37;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Exp extends Function {

    public Exp() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        double[] x0 = xs[0].values;
        int length = xs[0].length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.exp(x0[i]);
        }
        Tensor[] ys = new Tensor[1];
        ys[0] = new Tensor(values, xs[0].shape);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        Variable y = outputs[0];
        gx[0] = gys[0].times(y);
        gx[0].setShape(inputs[0].getShape());
        return gx;
    }
}
