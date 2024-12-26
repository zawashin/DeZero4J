package step.step44;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Parameter extends Variable {

    public Parameter(double value) {
        super(value);
    }

    public Parameter(double[] values) {
        super(values);
    }

    public Parameter(double[][] values) {
        super(values);
    }

    public Parameter(double[] values, int[] shape) {
        super(new Tensor(values, shape));
    }

    public Parameter(Tensor tensor) {
        super(tensor);
    }

    public Parameter(double values, int[] shape) {
        super(values, shape);
    }

}
