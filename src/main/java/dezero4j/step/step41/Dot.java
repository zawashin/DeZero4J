package dezero4j.step.step41;

import tensor4j.Operators;
import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Dot extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[1];
        ys[0] = xs[0].dot(xs[1]);
        //Operators.dot(xs);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[2];
        gxs[0] = gys[0].dot(inputs[1].transpose());
        gxs[1] = (inputs[0].transpose()).dot(gys[0]);
        return gxs;
    }
}
