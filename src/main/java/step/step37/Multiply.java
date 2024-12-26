package step.step37;

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
        /*
        Tensor[] xs = new Tensor[]{inputs[0].getData(), inputs[1].getData()};
        return new Tensor[]{xs[1].multiply(gys[0]), xs[0].multiply(gys[0])};

         */
        Variable[] xs = inputs;
        return new Variable[]{xs[1].multiply(gys[0]), xs[0].multiply(gys[0])};
    }

}
