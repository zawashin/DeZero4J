package dezero4j.step.step24;

import dlfs3.step.step24.Variable;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class MultivariateFunction {
    public abstract dlfs3.step.step24.Variable forward(dlfs3.step.step24.Variable... xs);

    public dlfs3.step.step24.Variable constant(int length, double data) {
        return new Variable(length, data);
    }
}
