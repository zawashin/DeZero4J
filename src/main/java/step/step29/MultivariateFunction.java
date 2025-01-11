package step.step29;

import tensor4j.TensorUtils;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class MultivariateFunction {
    protected int[] shape;

    public abstract Variable calc(Variable... xs);

    protected Variable c(double value) {
        return new Variable(TensorUtils.fill(value, shape));
    }
}
