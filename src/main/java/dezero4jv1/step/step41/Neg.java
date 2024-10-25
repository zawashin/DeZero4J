package dezero4jv1.step.step41;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Neg extends Function {

    public Neg() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorOperator.neg(xs[0]);

        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0].neg();
        gxs[0].setShape(inputs[0].getShape());

        return gxs;
    }
}
