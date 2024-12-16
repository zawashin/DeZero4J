package dezero4j.step.step38;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Subtract extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].subtract(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        //return new Tensor[]{gys[0], gys[0].neg()};
        return new Variable[]{gys[0].clone(), gys[0].neg()};
    }

}
