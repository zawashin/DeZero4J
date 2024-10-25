package dezero4jv1.step.step40;

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
        /*
        double[] x0 = xs[0].values;
        int length = x0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.sin(x0[i]);
        }
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = new Tensor(values, xs[0].shape);

         */
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorOperator.sin(xs[0]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = (inputs[0].cos()).times(gys[0]);
        gxs[0].setShape(inputs[0].getShape());
        return gxs;
    }

}
