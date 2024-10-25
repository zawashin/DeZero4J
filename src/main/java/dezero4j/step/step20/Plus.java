package dezero4j.step.step20;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Plus extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].plus(xs[1])};
    }

    @Override
    public Tensor[] backward(Tensor[] gys) {
        return new Tensor[]{gys[0], gys[0]};
    }
}
