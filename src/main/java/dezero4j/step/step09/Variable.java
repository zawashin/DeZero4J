package dezero4j.step.step09;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable implements Serializable {
    protected Tensor data;
    protected Tensor grad = null;
    protected Function creator = null;

    public Variable(double data) {
        this.data = new Tensor(data);
    }

    public Variable(double[] data) {
        this.data = new Tensor(data);
    }

    public Variable(double[][] data) {
        this.data = new Tensor(data);
    }

    public Variable(Tensor data) {
        this.data = data.clone();
    }

    public void backward() {
        if (grad == null) {
            grad = Utils.create(1.0, data.getShape());
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

    public Tensor getData() {
        return data;
    }

    public void setGrad(Tensor grad) {
        this.grad = grad;
    }

    public Tensor getGrad() {
        return grad;
    }

    public Function getCreator() {
        return creator;
    }

    public void setCreator(Function func) {
        this.creator = func;
    }

    public String toString() {
        return data.toString();
    }

    public int[] getShape() {
        return data.getShape();
    }

    public double[] getValues() {
        return data.getValues();
    }

    public Variable exp() {
        Function f = new Exp();
        return f.forward(this);
    }

    public Variable square() {
        Function f = new Square();
        return f.forward(this);
    }

}
