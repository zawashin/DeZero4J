package dezero4j.step.step34.step39;

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
        ys[0] = TensorOperator.minus(xs[0], xs[1]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0];
        gxs[1] = gys[0].neg();
        gxs[0].setShape(inputs[0].getShape());
        gxs[1].setShape(inputs[1].getShape());
        return gxs;
    }
}
