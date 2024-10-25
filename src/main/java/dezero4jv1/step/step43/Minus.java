package dezero4jv1.step.step43;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Minus extends Function {

    public Minus() {
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
        ys[0] = TensorOperator.minus(xs_);
        //ys[0] = TensorOperator.minus(xs_[0], xs_[1]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        if(!Arrays.equals(inputs[0].getShape(), inputs[1].getShape())) {
            gxs[0] = gys[0].sumTo(inputs[0].getShape());
            gxs[1] = (gys[0].neg()).sumTo(inputs[1].getShape());
        } else {
            gxs[0] = gys[0].clone();
            gxs[1] = (gys[0].clone()).neg();
        }
        return gxs;
    }
}
