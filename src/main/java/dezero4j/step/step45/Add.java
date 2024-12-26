package dezero4j.step.step45;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Add extends Function {
    boolean broadcast = false;

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] xs_ = new Tensor[2];
        if (!Arrays.equals(xs[0].getShape(), xs[1].getShape())) {
            broadcast = true;
            int[] shape_ = Utils.broadcastShape(xs[0].getShape(), xs[1].getShape());
            xs_[0] = xs[0].broadcastTo(shape_);
            xs_[1] = xs[1].broadcastTo(shape_);

        } else {
            xs_[0] = xs[0];
            xs_[1] = xs[1];
        }
        return new Tensor[]{xs_[0].add(xs_[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[2];
        if (broadcast) {
            gxs[0] = gys[0].sumTo(inputs[0].getShape());
            gxs[1] = gys[0].sumTo(inputs[1].getShape());
        } else {
            gxs[0] = gys[0].clone();
            gxs[1] = gys[0].clone();
        }
        return gxs;
    }
}
