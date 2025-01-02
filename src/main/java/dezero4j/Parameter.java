package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Parameter extends Variable {

    @Serial
    private static final long serialVersionUID = 6622643344649721724L;

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
