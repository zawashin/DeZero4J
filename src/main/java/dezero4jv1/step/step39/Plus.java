package dezero4jv1.step.step39;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Plus extends Function {

    public Plus() {
        numInputs = 2;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = x0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = x0[i] + x1[i];
        }
        Tensor[] ys = new Tensor[1];
        ys[0] = new Tensor(values, xs[0].shape);
        return ys;
    }

    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[2];
        gx[0] = gys[0];
        gx[1] = gys[0];
        gx[0].setShape(inputs[0].getShape());
        gx[1].setShape(inputs[1].getShape());
        return gx;
    }

}
