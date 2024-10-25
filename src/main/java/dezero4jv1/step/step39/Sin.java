package dezero4jv1.step.step39;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sin extends Function {

    public Sin() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        double[] x0 = xs[0].values;
        int length = x0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.sin(x0[i]);
        }
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = new Tensor(values, xs[0].shape);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        gx[0] = (inputs[0].cos()).times(gys[0]);
        gx[0].setShape(inputs[0].getShape());
        return gx;
    }

}
