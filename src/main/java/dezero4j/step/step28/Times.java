package dezero4j.step.step28;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Times extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].times(xs[1])};
    }

    @Override
    public Tensor[] backward(Tensor[] gys) {
        Tensor[] xs = new Tensor[]{inputs[0].getData(), inputs[1].getData()};
        return new Tensor[]{xs[1].times(gys[0]), xs[0].times(gys[0])};
    }

}
