package dezero4j.step.step32;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Neg extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].neg()};
    }

    @Override
    public Tensor[] backward(Tensor[] gys) {
        return new Tensor[]{gys[0].neg()};
    }

}
