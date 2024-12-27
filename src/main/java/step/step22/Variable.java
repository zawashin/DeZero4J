package step.step22;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    protected Tensor data;
    protected Tensor grad;
    protected Function creator;
    protected int generation;

    public Variable(double value) {
        this.data = new Tensor(value);
        this.grad = null;
        this.creator = null;
        this.generation = 0;
    }

    public Variable(double[] values) {
        this.data = new Tensor(values);
        this.grad = null;
        this.creator = null;
        this.generation = 0;
    }

    public Variable(double[][] values) {
        this.data = new Tensor(values);
        this.grad = null;
        this.creator = null;
        this.generation = 0;
    }

    public Variable(Tensor data) {
        this.data = data.clone();
    }

    public void backward() {
        if (grad == null) {
            grad = Utils.fill(1.0, data.getShape());
        }
        ArrayList<Function> funcs = new ArrayList<>();
        funcs.add(creator);
        Set<Function> seenSet = new HashSet<>();
        seenSet.add(creator);

        while (!funcs.isEmpty()) {
            Function f = funcs.removeLast();
            Tensor[] gys = new Tensor[f.outputs.length];
            for (int i = 0; i < f.outputs.length; i++) {
                gys[i] = f.outputs[i].grad;
            }
            Tensor[] gxs = f.backward(gys);
            if (gxs.length != f.inputs.length) {
                throw new IllegalStateException("Length of gradients and inputs do not match");
            }
            for (int i = 0; i < gxs.length; i++) {
                Variable x = f.inputs[i];

                if (f.inputs[i].grad == null) {
                    //f.inputs[i].grad = gxs[i];
                    // 複製しないとダメ
                    f.inputs[i].grad = gxs[i].clone();
                } else {
                    f.inputs[i].grad.addAssign(gxs[i]);
                }

                if (x.getCreator() != null) {
                    if (!seenSet.contains(x.creator)) {
                        funcs.add(x.creator);
                        seenSet.add(x.creator);
                        funcs.sort(Comparator.comparingInt(Function::getGeneration));
                    }
                }
            }
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

    public int getGeneration() {
        return generation;
    }

    public Function getCreator() {
        return creator;
    }

    public void setCreator(Function func) {
        this.creator = func;
        this.generation = func.getGeneration() + 1;
    }

    public void cleaGrad() {
        grad = null;
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

    public Variable add(Variable other) {
        Function f = new Add();
        return f.forward(this, other)[0];
    }

    public Variable add(double other) {
        Function f = new Add();
        return f.forward(this, new Variable(Utils.fill(other, this.getShape())))[0];
    }

    public Variable subtract(Variable other) {
        Function f = new Subtract();
        return f.forward(this, other)[0];
    }

    public Variable subtract(double other) {
        Function f = new Subtract();
        return f.forward(this, new Variable(Utils.fill(other, this.getShape())))[0];
    }

    public Variable rminus(Variable other) {
        Function f = new Subtract();
        return f.forward(other, this)[0];
    }

    public Variable rminus(double other) {
        Function f = new Subtract();
        return f.forward(new Variable(Utils.fill(other, this.getShape())), this)[0];
    }

    public Variable multiply(Variable other) {
        Function f = new Multiply();
        return f.forward(this, other)[0];
    }

    public Variable multiply(double other) {
        Function f = new Multiply();
        return f.forward(new Variable(Utils.fill(other, this.getShape())), this)[0];
    }

    public Variable divide(Variable other) {
        Function f = new Divide();
        return f.forward(this, other)[0];
    }

    public Variable divide(double other) {
        Function f = new Divide();
        return f.forward(this, new Variable(Utils.fill(other, this.getShape())))[0];
    }

    public Variable rdiv(Variable other) {
        Function f = new Divide();
        return f.forward(other, this)[0];
    }

    public Variable rdiv(double other) {
        Function f = new Divide();
        return f.forward(new Variable(Utils.fill(other, this.getShape())), this)[0];
    }

    public Variable exp() {
        Function f = new Exp();
        return f.forward(this)[0];
    }

    public Variable square() {
        Function f = new Square();
        return f.forward(this)[0];
    }

    public Variable pow(double index) {
        Function f = new Pow(index);
        return f.forward(this)[0];
    }


}