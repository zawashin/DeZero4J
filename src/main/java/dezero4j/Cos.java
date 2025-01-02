package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Cos extends Function {

    @Serial
    private static final long serialVersionUID = -1675730460216274125L;

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].cos()};
    }

    @Override
    public Variable[] backward(Variable[] gys) {
        Variable x = inputs[0];
        return new Variable[]{x.sin().multiply(gys[0]).neg()};
    }

}
