package dezero4j.step.step40;

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
        //ys[0] = TensorOperator.minus(xs_[0], xs_[1]);
        ys[0] = xs_[0].minus(xs_[1]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0].clone();
        gxs[1] = (gys[0].clone()).neg();
        gxs[0].setShape(inputs[0].getShape());
        gxs[1].setShape(inputs[1].getShape());
        return gxs;
    }
}
