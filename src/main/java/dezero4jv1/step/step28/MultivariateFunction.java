package dezero4jv1.step.step28;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class MultivariateFunction {
    public abstract Variable forward(Variable... xs);

    public Variable constant(int length, double data) {
        return new Variable(length, data);
    }
}
