package dezero4j.step.step11;

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
        Tensor[] xs = new Tensor[]{getInput(0).getData(), getInput(1).getData()};
        return new Tensor[]{xs[0].times(gys[0]), xs[1].times(gys[0])};
    }
}
