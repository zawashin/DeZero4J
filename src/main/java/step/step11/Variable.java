package step.step11;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    protected Tensor data;
    protected Tensor grad;
    protected Function creator;

    public Variable(double data) {
        this.data = new Tensor(data);
        this.grad = null;
        this.creator = null;
    }

    public Variable(double[] values) {
        this.data = new Tensor(values);
        this.grad = null;
        this.creator = null;
    }

    public Variable(double[][] values) {
        this.data = new Tensor(values);
        this.grad = null;
        this.creator = null;
    }

    public Variable(Tensor data) {
        this.data = data.clone();
    }

    public void backward() {
        if (grad == null) {
            grad = Utils.fill(1.0, data.getShape());
        }

        Function[] funcs = {creator};
        int index = 0;
        while (index < funcs.length) {
            Function f = funcs[index];
            Variable x = f.getInput(0);
            Variable y = f.getOutput(0);
            x.setGrad(f.backward(new Tensor[]{y.getGrad()})[0]);

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

    public Tensor getGrad() {
        return grad;
    }

    public void setGrad(Tensor grad) {
        this.grad = grad;
    }

    public Function getCreator() {
        return creator;
    }

    public void setCreator(Function func) {
        this.creator = func;
    }

    public String toString() {
        return this.data.toString();
    }

    public int getLength() {
        return data.getLength();
    }

    public int[] getShape() {
        return data.getShape();
    }

    public double[] getValues() {
        return data.getValues();
    }

    public Variable exp() {
        Function f = new Exp();
        return f.forward(new Variable[]{this})[0];
    }

    public Variable square() {
        Function f = new Square();
        return f.forward(new Variable[]{this})[0];
    }

    public Variable add(Variable other) {
        Function f = new Add();
        return f.forward(new Variable[]{this, other})[0];
    }

    public Variable add(double other) {
        Function f = new Add();
        return f.forward(new Variable[]{this, new Variable(other)})[0];
    }

}
