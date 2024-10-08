package dezero4j.step.step42;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class LossFunction extends Function {

    public Variable calcLoss(Variable... xs) {
        return forward(xs)[0];
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return null;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return null;
    }
}
