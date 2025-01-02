package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ReLU extends ActivationFunction {

    @Serial
    private static final long serialVersionUID = -5790618777134224158L;

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[1];
        Tensor x = xs[0];
        int length = x.getLength();
        double[] values = new double[length];
        for (int i = 0; i < x.getLength(); i++) {
            values[i] = Math.max(x.getValues()[i], 0.0);
        }
        ys[0] = new Tensor(values);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[1];

        double[] values = new double[gys[0].getLength()];
        for (int i = 0; i < values.length; i++) {
            values[i] = inputs[0].getValues()[i] > 0 ? gys[0].getValues()[i] : 0;
        }
        gxs[0] = new Variable(values);

        return gxs;
    }
}
