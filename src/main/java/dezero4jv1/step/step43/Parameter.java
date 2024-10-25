package dezero4jv1.step.step43;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Parameter extends Variable {
    public Parameter(double data) {
        super(data);
    }

    public Parameter(double data, int length) {
        super(data, length);
    }

    public Parameter(double data, int[] shape) {
        super(data, shape);
    }

    public Parameter(double[] data) {
        super(data);
    }

    public Parameter(double[] data, int[] shape) {
        super(data, shape);
    }

    public Parameter(double[][] data) {
        super(data);
    }

    public Parameter(Tensor data) {
        super(data);
    }
}
