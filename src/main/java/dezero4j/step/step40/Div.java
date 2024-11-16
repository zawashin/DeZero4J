package dezero4j.step.step40;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Div extends Function {
    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].div(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] xs = inputs;
        return new Variable[]{gys[0].div(xs[0]), gys[0].neg().times(xs[0].div(xs[1].square()))};
    }

}
