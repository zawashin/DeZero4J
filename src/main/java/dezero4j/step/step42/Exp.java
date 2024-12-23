package dezero4j.step.step42;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Exp extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].exp()};
    }

    public Variable[] backward(Variable... gys) {
        Variable x = getInput(0);
        return new Variable[]{x.exp().multiply(gys[0])};
    }
}
