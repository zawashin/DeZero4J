package step.step43;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sin extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].sin()};
    }

    @Override
    public Variable[] backward(Variable[] gys) {
        Variable x = inputs[0];
        return new Variable[]{x.cos().multiply(gys[0])};

    }

}
