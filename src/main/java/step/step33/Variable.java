package step.step33;

import tensor4j.Tensor;
import tensor4j.TensorUtils;

import java.io.Serializable;
import java.util.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable implements Cloneable, Serializable {
    protected Tensor data;
    protected Variable grad;
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

    public Variable(Variable variable) {
        this.data = variable.data.clone();
        if (variable.grad != null) {
            this.grad = variable.grad.clone();
        }
        if (variable.creator != null) {
            this.creator = variable.creator.clone();
        }
        this.generation = variable.generation;
    }

    @Override
    public Variable clone() {
        return clone(new IdentityHashMap<>());
    }

    protected Variable clone(Map<Variable, Variable> clones) {
        // すでにクローンされたインスタンスがある場合はそれを返す
        if (clones.containsKey(this)) {
            return clones.get(this);
        }

        try {
            Variable cloned = (Variable) super.clone();
            clones.put(this, cloned); // クローンをマップに登録
            cloned.data = data != null ? data.clone() : null;
            cloned.grad = grad != null ? grad.clone(clones) : null; // 再帰的にクローン
            cloned.creator = creator;  // Functionに対する処理（必要ならdeep copyする）
            cloned.generation = generation;
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void backward(boolean retainGrad, boolean createGraph) {
        if (grad == null) {
            grad = new Variable(TensorUtils.fill(1.0, data.getShape()));
        }
        ArrayList<Function> funcs = new ArrayList<>();
        Set<Function> seenSet = new HashSet<>();
        funcs.add(creator);
        seenSet.add(creator);
        funcs.sort((f1, f2) -> Integer.compare(f1.getGeneration(), f2.getGeneration()));
        while (!funcs.isEmpty()) {
            Function f = funcs.removeLast();
            Variable[] inputs = f.getInputs();
            Variable[] gys = new Variable[f.outputs.length];
            for (int i = 0; i < f.outputs.length; i++) {
                if (f.outputs[i].grad == null) {
                    gys[i] = new Variable(TensorUtils.fill(1.0, data.getShape()));
                } else {
                    gys[i] = f.outputs[i].grad;
                }
            }
            try (UsingConfig config = new UsingConfig("enableBackprop", createGraph)) {
                if (createGraph) {
                    this.generation = Arrays.stream(inputs)
                            .mapToInt(Variable::getGeneration)
                            .max()
                            .orElse(0);
                    Variable[] gxs = f.backward(gys);
                    if (gxs.length != f.inputs.length) {
                        throw new IllegalStateException("Length of gradients and inputs do not match");
                    }
                    for (int i = 0; i < gxs.length; i++) {
                        Variable x = f.inputs[i];
                        Variable gx = gxs[i];

                        if (x.grad == null) {
                            x.grad = gx;
                        } else {
                            x.grad = x.grad.add(gx);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!retainGrad) {
                for (int i = 0; i < f.outputs.length; i++) {
                    f.outputs[i].grad = null;//  #y is weakref
                }
            }
        }
    }

    public void backward() {
        backward(true, true);
    }

    public Tensor getData() {
        return data;
    }

    public Variable getGrad() {
        return grad;
    }

    public void setGrad(Tensor grad) {
        this.grad = new Variable(grad);
    }

    public void clearGrad() {
        grad = null;
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
        return f.forward(this, new Variable(TensorUtils.fill(other, this.getShape())))[0];
    }

    public void plusAssign(Variable other) {
        data.addAssign(other.data);
    }

    public Variable subtract(Variable other) {
        Function f = new Subtract();
        return f.forward(this, other)[0];
    }

    public Variable subtract(double other) {
        Function f = new Subtract();
        return f.forward(this, new Variable(TensorUtils.fill(other, this.getShape())))[0];
    }

    public void minusAssign(Variable other) {
        data.subtractAssign(other.data);
    }

    public Variable rminus(Variable other) {
        Function f = new Subtract();
        return f.forward(other, this)[0];
    }

    public Variable rminus(double other) {
        Function f = new Subtract();
        return f.forward(new Variable(TensorUtils.fill(other, this.getShape())), this)[0];
    }

    public Variable multiply(Variable other) {
        Function f = new Multiply();
        return f.forward(this, other)[0];
    }

    public Variable multiply(double other) {
        Function f = new Multiply();
        return f.forward(new Variable(TensorUtils.fill(other, this.getShape())), this)[0];
    }

    public Variable divide(Variable other) {
        Function f = new Divide();
        return f.forward(this, other)[0];
    }

    public Variable divide(double other) {
        Function f = new Divide();
        return f.forward(this, new Variable(TensorUtils.fill(other, this.getShape())))[0];
    }

    public Variable rdiv(Variable other) {
        Function f = new Divide();
        return f.forward(other, this)[0];
    }

    public Variable rdiv(double other) {
        Function f = new Divide();
        return f.forward(new Variable(TensorUtils.fill(other, this.getShape())), this)[0];
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
        Function f = new Power(index);
        return f.forward(this)[0];
    }

    public Variable neg() {
        Function f = new Neg();
        return f.forward(this)[0];
    }

    public Variable cos() {
        Function f = new Cos();
        return f.forward(this)[0];
    }

    public Variable sin() {
        Function f = new Sin();
        return f.forward(this)[0];
    }
}
