package dezero4j.step.step42;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Divide extends Function {

    public Divide() {
        numInputs = 2;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        int[] broadcast = TensorUtils.broadcastShape(xs[0], xs[1]);
        Tensor[] xs_ = new Tensor[numInputs];
        xs_[0] = xs[0].broadcastTo(broadcast);
        xs_[1] = xs[1].broadcastTo(broadcast);
        ys[0] = TensorOperator.divide(xs_);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable[] xs = new Variable[numInputs];
        xs[0] = inputs[0];
        xs[1] = inputs[1];
        if(!Arrays.equals(inputs[0].getShape(), inputs[1].getShape())) {
            gxs[0] = (gys[0].divide(xs[1])).sumTo(xs[0].getShape());
            gxs[1] = (gys[0].neg().multiply(xs[0].divide(xs[1].pow(2)))).sumTo(xs[1].getShape());
        } else {
            gxs[0] = (gys[0].divide(xs[1]));
            gxs[1] = (gys[0].neg().multiply(xs[0].divide(xs[1].pow(2))));
        }
        return gxs;
    }
}
