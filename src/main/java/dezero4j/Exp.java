package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Exp extends Function {

    @Serial
    private static final long serialVersionUID = 7557350109206898622L;

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].exp()};
    }

    public Variable[] backward(Variable... gys) {
        Variable x = getInput(0);
        return new Variable[]{x.exp().multiply(gys[0])};
    }
}
