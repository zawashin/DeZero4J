package step.step35;

import tensor4j.Utils;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class MultivariateFunction {
    protected int[] shape;

    public abstract Variable calc(Variable... xs);

    protected Variable c(double value) {
        return new Variable(Utils.fill(value, shape));
    }
}
