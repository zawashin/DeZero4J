package step.step29;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].square()};
    }

    @Override
    public Tensor[] backward(Tensor[] gys) {
        Tensor x = getInputs()[0].getData();
        return new Tensor[]{x.multiply(gys[0]).multiply(2)};
    }
}
