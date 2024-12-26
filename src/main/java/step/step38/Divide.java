package step.step38;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Divide extends Function {
    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].divide(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] xs = inputs;
        return new Variable[]{gys[0].divide(xs[0]), gys[0].neg().multiply(xs[0].divide(xs[1].square()))};
    }

}
