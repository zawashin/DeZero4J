package dezero4j.step.step33;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class LostFunction extends Function {
    public Variable calc(Variable... xs) {
        inputs = xs;
        return forward(xs)[0];
    }

    @Override
    public double[][] forward(double[]... xs) {
        return null;
    }

    public Variable[] backward(Variable... gys) {
        return null;
    }
}
