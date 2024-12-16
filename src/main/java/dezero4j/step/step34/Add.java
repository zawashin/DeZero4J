package dezero4j.step.step34;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Add extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].add(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        //return new Tensor[]{gys[0], gys[0]};
        return new Variable[]{gys[0].clone(), gys[0].clone()};
    }
}
