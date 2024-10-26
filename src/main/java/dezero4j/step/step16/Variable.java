package dezero4j.step.step16;

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

    public Variable(double data) {
        this.data = new Tensor(data);
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

    public void setCreator(Function func) {
        this.creator = func;
        this.generation = func.getGeneration() + 1;
    }

    public void backward() {
        if (grad == null) {
            grad = Utils.create(1.0, data.getShape());
        }
        ArrayList<Function> funcs = new ArrayList<>();
        funcs.add(creator);
        Set<Function> seenSet = new HashSet<>();
        seenSet.add(creator);

        while (!funcs.isEmpty()) {
            Function f = funcs.remove(funcs.size() - 1);
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
                    // 複製しないとダメっぽい
                    f.inputs[i].grad = gxs[i].clone();
                } else {
                    f.inputs[i].grad.plusAssign(gxs[i]);
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

    public Variable exp() {
        Function f = new Exp();
        return f.forward(this)[0];
    }

    public Variable square() {
        Function f = new Square();
        return f.forward(this)[0];
    }

    public Variable plus(Variable other) {
        Function f = new Plus();
        return f.forward(this, other)[0];
    }

    public Variable plus(double other) {
        Function f = new Plus();
        return f.forward(this, new Variable(Utils.create(other, this.getShape())))[0];
    }

}
