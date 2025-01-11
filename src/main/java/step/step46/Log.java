package step.step46;

import tensor4j.TensorOperators;
import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Log extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{TensorOperators.log(xs[0])
        };
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[1];
        Variable x = inputs[0];
        gxs[0] = gys[0].divide(inputs[0]);
        gxs[0].reshape(inputs[0].getShape());

        return gxs;
    }

}
