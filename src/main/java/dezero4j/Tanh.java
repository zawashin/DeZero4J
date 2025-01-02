package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Tanh extends Function {

    @Serial
    private static final long serialVersionUID = 7705887820050495684L;

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].tanh()};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable y = outputs[0];
        Variable gx = gys[0].multiply(y.pow(2).rSubtract(1));
        return new Variable[]{gx};
        //return Variable[]{gys[0].multiply(y.pow(2).rminus(1))};
    }
}
