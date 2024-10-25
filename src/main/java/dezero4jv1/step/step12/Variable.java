package dezero4jv1.step.step12;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    private final double[] data;
    private double[] grad;
    private Function creator;

    public Variable(double[] data) {
        this.data = data.clone();
        this.grad = null;
        this.creator = null;
    }

    public Variable(double data) {
        this.data = new double[]{data};
        this.grad = null;
        this.creator = null;
    }
    public void setCreator(Function func) {
        this.creator = func;
    }

    public void backward() {
        if (grad == null) {
            grad = new double[data.length];
            Arrays.fill(grad, 1.0);
        }

        Function[] funcs = {creator};
        int index = 0;
        while (index < funcs.length) {
            Function f = funcs[index];
            Variable x = f.getInputs()[0];

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
