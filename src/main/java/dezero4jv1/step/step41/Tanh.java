package dezero4jv1.step.step41;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Tanh extends Function {

    public Tanh() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorOperator.tanh(xs[0]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable gy = gys[0];
        Variable y = outputs[0];
        int length = gys[0].getLength();
        //gxs[0] = gy.times(param(1, length).minus(y.pow(2)));
        gxs[0] = gy.times(param(1, gys[0].getShape()).minus(y.pow(2)));
        gxs[0].setShape(inputs[0].getShape());

        return gxs;
    }
}
