package step.step09;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {
    @Override
    public Tensor forward(Tensor x) {
        return x.square();
    }

    @Override
    public Tensor backward(Tensor gy) {
        Tensor x = getInput().getData();
        return x.multiply(gy).multiply(2);
    }
}
