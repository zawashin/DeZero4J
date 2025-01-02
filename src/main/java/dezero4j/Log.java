package dezero4j;

import tensor4j.Operators;
import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Log extends Function {

    @Serial
    private static final long serialVersionUID = -8205250995439686599L;

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{Operators.log(xs[0])
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
