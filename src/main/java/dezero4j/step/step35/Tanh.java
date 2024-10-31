package dezero4j.step.step35;

import tensor4j.Tensor;
import tensor4j.Utils;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Tanh extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].tanh()};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable y = outputs[0];
        Variable gx = gys[0].times(y.pow(2).rminus(1));
        return new Variable[]{gx};
        //return Variable[]{gys[0].times(y.pow(2).rminus(1))};
    }
}