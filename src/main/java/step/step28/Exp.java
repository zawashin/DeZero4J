package step.step28;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Exp extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].exp()};
    }

    @Override
    public Tensor[] backward(Tensor[] gys) {
        Tensor x = getInput(0).getData();
        return new Tensor[]{x.exp().multiply(gys[0])};
    }
}
