package dezero4jv1;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ReLU extends ActivationFunction {

    public ReLU() {
        numInputs = 1;
        numOutputs  = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        Tensor x = xs[0];
        int length = x.getLength();
        double[] values = new double[length];
        for (int i = 0; i < x.length; i++) {
            values[i] = Math.max(x.getValues()[i], 0.0);
        }
        ys[0] = new Tensor(values);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];

        double[] values = new double[gys[0].getLength()];
        for (int i = 0; i < values.length; i++) {
            values[i] = inputs[0].getValues()[i] > 0 ? gys[0].getValues()[i] : 0;
        }
        gxs[0] = new Variable(values);

        return gxs;
    }
}
