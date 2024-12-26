package dezero4j.step.step46;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Identify extends ActivationFunction {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return xs;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return gys;
    }
}
