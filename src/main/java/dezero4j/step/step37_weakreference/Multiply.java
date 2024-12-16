package dezero4j.step.step37_weakreference;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Multiply extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].multiply(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] xs = inputs;
        return new Variable[]{xs[1].multiply(gys[0]), xs[0].multiply(gys[0])};
    }

}
