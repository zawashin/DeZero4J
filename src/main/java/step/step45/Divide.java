package step.step45;

import tensor4j.Tensor;
import tensor4j.TensorUtils;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Divide extends Function {
    boolean broadcast = false;
    int[] shape;

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] xs_ = new Tensor[2];
        if (!Arrays.equals(xs[0].getShape(), xs[1].getShape())) {
            broadcast = true;
            shape = TensorUtils.broadcastShape(xs[0].getShape(), xs[1].getShape());
            xs_[0] = xs[0].broadcastTo(shape);
            xs_[1] = xs[1].broadcastTo(shape);

        } else {
            xs_[0] = xs[0];
            xs_[1] = xs[1];
        }
        return new Tensor[]{xs_[0].divide(xs_[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[2];
        Variable[] xs = new Variable[2];
        if (broadcast) {
            xs[0] = inputs[0].broadcastTo(shape);
            xs[1] = inputs[1].broadcastTo(shape);
            gxs[0] = gys[0].divide(xs[1]);
            gxs[1] = gys[0].neg().multiply(xs[0].divide(xs[1].square()));
            gxs[0] = gxs[0].sumTo(inputs[0].getShape());
            gxs[1] = gxs[1].sumTo(inputs[1].getShape());
        } else {
            xs[0] = inputs[0];
            xs[1] = inputs[1];
            gxs[0] = gys[0].divide(xs[1]);
            gxs[1] = gys[0].neg().multiply(xs[0].divide(xs[1].square()));
        }

        return gxs;
    }
}
