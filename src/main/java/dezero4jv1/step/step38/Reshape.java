package dezero4jv1.step.step38;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Reshape extends Function {
    int[] shape;

    public Reshape() {
        numInputs = 1;
        numOutputs = 1;
    }

    public Reshape(int[] shape) {
        numInputs = 1;
        numOutputs = 1;
        this.shape = shape.clone();
    }

    public void setShape(int[] shape) {
        this.shape = shape.clone();
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        double[] x0 = xs[0].values;
        if(shape == null) {
           shape = xs[0].getShape();
        }
        int length = x0.length;
        double[] values = new double[length];
        System.arraycopy(xs[0].values, 0, values, 0, length);
        switch(xs[0].rank) {
            case 0, 1:
                break;
            case 2:
                int n = 0;
                for (int i = 0; i < xs[0].shape[0]; i++) {
                    for (int j = 0; j < xs[0].shape[1]; j++) {
                        values[n++] = xs[0].values[i * xs[0].num2 + j];
                    }
                }
                break;
            case 3:
            case 4:
            default:
                throw new RuntimeException("Not implemented yet");
        }
        ys[0] = new Tensor(values, shape);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        gx[0] = gys[0].reshape(inputs[0].getShape());
        gx[0].reshape(shape);
        return gx;
    }
}
