package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {

    @Serial
    private static final long serialVersionUID = 8012948576188584835L;

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].square()};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        /*j
        Tensor x = getInputs()[0].getData();
        return new Tensor[]{x.multiply(gys[0]).multiply(2)};

         */
        Variable x = getInputs()[0];
        return new Variable[]{x.multiply(gys[0]).multiply(2)};
    }
}
