package dezero4jv1.step.step45;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class LogSoftmax extends ActivationFunction {

    public LogSoftmax() {
        numInputs = 1;
        numOutputs  = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[0];
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return new Variable[0];
    }
}
