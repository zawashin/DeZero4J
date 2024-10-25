package dezero4jv1.step.step35;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class LostFunction extends Function {
    public Variable calc(Variable... xs) {
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
