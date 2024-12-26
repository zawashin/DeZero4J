package step.step32;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Neg extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].neg()};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        //return new Tensor[]{gys[0].neg()};
        return new Variable[]{gys[0].neg()};
    }

}
