package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Identify extends ActivationFunction {

    @Serial
    private static final long serialVersionUID = -1675730460216274125L;

    @Override
    public Tensor[] forward(Tensor... xs) {
        return xs;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return gys;
    }
}
