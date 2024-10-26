package dezero4j.step.step14;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.ArrayList;

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

    public void setCreator(Function func) {
        this.creator = func;
    }

    public void backward() {
        if (grad == null) {
            grad = Utils.create(1.0, data.getShape());
        }
        ArrayList<Function> funcList = new ArrayList<>();
        funcList.add(creator);
        while (!funcList.isEmpty()) {
            Function f = funcList.remove(funcList.size() - 1);
            Tensor[] gys = new Tensor[f.outputs.length];
            for (int i = 0; i < f.outputs.length; i++) {
                gys[i] = f.outputs[i].grad;
            }
            Tensor[] gxs = f.backward(gys);
            if (gxs.length != f.inputs.length) {
                throw new IllegalStateException("Length of gradients and inputs do not match");
            }
            for (int i = 0; i < gxs.length; i++) {
                if (f.inputs[i].grad == null) {
                    //f.inputs[i].grad = gxs[i];
                    // 複製しないとダメっぽい
                    f.inputs[i].grad = gxs[i].clone();
                } else {
                    f.inputs[i].grad.plusAssign(gxs[i]);
                }

                if (f.inputs[i].creator != null) {
                    funcList.add(f.inputs[i].creator);
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
        return f.forward(this, new Variable(other))[0];
    }

}
