package dezero4j.step.step41;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Dot extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].dot(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return new Variable[]{
                gys[0].dot(inputs[1].transpose()),
                inputs[0].transpose().dot(gys[0])};
    }
}
