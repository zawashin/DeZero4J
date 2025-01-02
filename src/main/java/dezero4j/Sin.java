package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sin extends Function {

    @Serial
    private static final long serialVersionUID = 2681905246712555371L;

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].sin()};
    }

    @Override
    public Variable[] backward(Variable[] gys) {
        Variable x = inputs[0];
        return new Variable[]{x.cos().multiply(gys[0])};

    }

}
