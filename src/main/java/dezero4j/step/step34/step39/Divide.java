package dezero4j.step.step34.step39;

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
        ys[0] = TensorOperator.divide(xs[0], xs[1]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable[] xs = new Variable[numInputs];
        xs[0] = inputs[0];
        xs[1] = inputs[1];
        gxs[0] = gys[0].divide(xs[0]);
        gxs[1] = gys[0].neg().multiply(xs[0].divide(xs[1].pow(2)));
        gxs[0].setShape(inputs[0].getShape());
        gxs[1].setShape(inputs[1].getShape());
        return gxs;
    }
}
