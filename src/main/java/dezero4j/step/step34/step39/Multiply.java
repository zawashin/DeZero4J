package dezero4j.step.step34.step39;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Multiply extends Function {

    public Multiply() {
        numInputs = 2;
        numOutputs = 1;
    }

    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorOperator.multiply(xs[0], xs[1]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable[] xs = new Variable[numInputs];
        xs[0] = inputs[0];
        xs[1] = inputs[1];
        gxs[0] = gys[0].multiply(xs[1]);
        gxs[1] = gys[0].multiply(xs[0]);
        return gxs;
    }
}
