package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Neg extends Function {

    @Serial
    private static final long serialVersionUID = -9014337093353943587L;

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].neg()};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        //return new Tensor[]{gys[0].neg()};
        return new Variable[]{gys[0].neg()};
    }

}
