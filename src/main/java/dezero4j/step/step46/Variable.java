package dezero4j.step.step46;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable implements Cloneable, Serializable {
    @Serial
    private static final long serialVersionUID = -5871953224193632639L;
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

    public Variable(double v, int[] shape) {
        this.data = Utils.fill(v, shape);
        this.grad = null;
        this.creator = null;
        this.generation = 0;
    }

    @Override
    public Variable clone() {
        try {
            Variable clone = (Variable) super.clone();
            // TODO: このクローンが元の内部を変更できないようにミュータブルな状態をここにコピーします
            if (data != null) {
                clone.data = data.clone();
            }
            if (grad != null) {
                clone.grad = grad.clone();
            }
            if (creator != null) {
                clone.creator = creator.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void backward(boolean retainGrad, boolean createGraph) {
        if (grad == null) {
            grad = new Variable(Utils.fill(1.0, getShape()));
        }
        ArrayList<Function> funcs = new ArrayList<>();
        Set<Function> seenSet = new HashSet<>();
        funcs.add(creator);
        seenSet.add(creator);
        funcs.sort(Comparator.comparingInt(Function::getGeneration));
        while (!funcs.isEmpty()) {
            Function f = funcs.removeLast();
            Variable[] inputs = f.getInputs();
            Variable[] gys = new Variable[f.outputs.length];
            for (int i = 0; i < f.outputs.length; i++) {
                if (f.outputs[i].grad == null) {
                    gys[i] = new Variable(Utils.fill(1.0, data.getShape()));
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

    public int getRank() {
        return data.getRank();
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

    public void addAssign(Variable other) {
        data.addAssign(other.data);
    }

    public Variable subtract(Variable other) {
        Function f = new Subtract();
        return f.forward(this, other)[0];
    }

    public Variable subtract(double other) {
        Function f = new Subtract();
        return f.forward(this, new Variable(Utils.fill(other, this.getShape())))[0];
    }

    public void subtractAssign(Variable other) {
        data.subtractAssign(other.data);
    }

    public Variable rSubtract(Variable other) {
        Function f = new Subtract();
        return f.forward(other, this)[0];
    }

    public Variable rSubtract(double other) {
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

    public Variable rDivide(Variable other) {
        Function f = new Divide();
        return f.forward(other, this)[0];
    }

    public Variable rDivide(double other) {
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

    public Variable tanh() {
        Function f = new Tanh();
        return f.forward(this)[0];
    }

    public Variable transpose(int... axes) {
        Function f = new Transpose(axes);
        return f.forward(this)[0];
    }

    public Variable reshape(int... shape) {
        Function f = new Reshape(shape);
        return f.forward(this)[0];
    }

    public Variable sum() {
        Function f = new Sum(-1);
        return f.forward(this)[0];
    }

    public Variable sum(int axis) {
        Function f = new Sum(axis);
        return f.forward(this)[0];
    }

    public Variable broadcastTo(int... shape) {
        Function f = new BroadcastTo(shape);
        return f.forward(this)[0];
    }

    public Variable sumTo(int... shape) {
        Function f = new SumTo(shape);
        return f.forward(this)[0];
    }

    public Variable matmul(Variable other) {
        Function f = new Matmul();
        return f.forward(this, other)[0];
    }

    public Variable mse(Variable other) {
        Function f = new MeanSquaredError();
        return f.forward(this, other)[0];
    }

    public Variable sigmoid() {
        Function f = new Sigmoid();
        return f.forward(this)[0];
    }

    public Variable linear(Variable w, Variable b) {
        Function function = new Linear();
        return function.forward(this, w, b)[0];
    }

    public Variable linear(Variable w) {
        Function function = new Linear();
        return function.forward(this, w)[0];
    }

    public Variable log(Variable w) {
        Function function = new Log();
        return function.forward(this, w)[0];
    }

}
