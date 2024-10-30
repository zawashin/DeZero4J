package dezero4j.step.step33_2;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Minus extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].minus(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        //return new Tensor[]{gys[0], gys[0].neg()};
        return new Variable[]{gys[0].clone(), gys[0].neg()};
    }

}
