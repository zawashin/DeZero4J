package dezero4j.step.step09;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Exp extends Function {
    @Override
    public Tensor forward(Tensor x) {
        return x.exp();
    }

    @Override
    public Tensor backward(Tensor gy) {
        Tensor x = getInput().getData();
        return x.exp().multiply(gy);
    }
}
