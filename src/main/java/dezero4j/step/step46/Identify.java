package dezero4j.step.step46;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Identify extends ActivationFunction {

    public Identify() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return xs;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return gys;
    }
}
