package dezero4j.step.step33;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Cos extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].cos()};
    }

    @Override
    public Variable[] backward(Variable[] gys) {
        Variable x = inputs[0];
        return new Variable[]{x.sin().times(gys[0]).neg()};
    }

}
