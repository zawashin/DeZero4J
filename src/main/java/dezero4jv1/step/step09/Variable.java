package dezero4jv1.step.step09;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    protected double[] data;
    protected double[] grad;
    protected Function creator;

    public Variable(double[] data) {
        if (data != null) {
            if (!(data instanceof double[])) {
                throw new IllegalArgumentException(data.getClass() + " is not supported");
            }
        }

        this.data = data.clone();
        this.grad = null;
        this.creator = null;
    }

    public void setCreator(Function func) {
        this.creator = func;
    }

    public void backward() {
        if (grad == null) {
            // grad = new ArrayRealVector(data.getDimension(), 1.0);
            grad = new double[data.length];
            Arrays.fill(grad, 1.0);
        }

        Function[] funcs = {creator};
        int index = 0;
        while (index < funcs.length) {
            Function f = funcs[index];
            Variable x = f.getInput();
            Variable y = f.getOutput();
            x.setGrad(f.backward(y.getGrad()));

            if (x.getCreator() != null) {
                funcs = Arrays.copyOf(funcs, funcs.length + 1);
                funcs[funcs.length - 1] = x.getCreator();
            }

            index++;
        }
    }

    public double[] getData() {
        return data;
    }

    public double[] getGrad() {
        return grad;
    }

    public void setGrad(double[] grad) {
        this.grad = grad;
    }

    public Function getCreator() {
        return creator;
    }
}
