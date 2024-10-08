package dezero4j.step.step38;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class LostFunction extends Function {
    public Variable calc(Variable... xs) {
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
