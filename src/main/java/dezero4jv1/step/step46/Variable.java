package dezero4jv1.step.step46;

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

    public Variable(double data) {
        this.data = new Tensor(data);
        this.grad = null;
        this.creator = null;
        generation = 0;
    }

    public Variable(double data, int length) {
        double[] values = new double[length];
        Arrays.fill(values, data);
        this.data = new Tensor(values);
        this.grad = null;
        this.creator = null;
        generation = 0;
    }

    public Variable(double data, int[] shape) {
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        double[] values = new double[length];
        Arrays.fill(values, data);
        this.data = new Tensor(values, shape);
        this.grad = null;
        this.creator = null;
        generation = 0;
    }
    public Variable(double[] data) {
        this.data = new Tensor(data);
        this.grad = null;
        this.creator = null;
        generation = 0;
    }

    public Variable(double[] data, int[] shape) {
        this.data = new Tensor(data, shape);
        this.grad = null;
        this.creator = null;
        generation = 0;
    }

    public Variable(double[][] data) {
        this.data = new Tensor(data);
        this.grad = null;
        this.creator = null;
        generation = 0;
    }

    public Variable(Tensor data) {
        this.data = data.clone();
        this.grad = null;
        this.creator = null;
        generation = 0;
    }

    public int getGeneration() {
        return generation;
    }

    public int getRank() {
        return data.rank;
    }

    public int[] getShape() {
        return data.getShape();
    }

    public void setShape(int[] shape) {
        data.setShape(shape);
    }

    public int getLength() {
        return data.length;
    }

    public void clearGrad() {
        grad = null;
    }

    public void backward() {
        backward(false, false);
    }

    public void backward(boolean retainGrad, boolean createGraph) {
        if (grad == null) {
            double[] g = new double[getLength()];
            Arrays.fill(g, 1);
            grad = new Variable(g, getShape());
        }
        ArrayList<Function> functionList = new ArrayList<>();
        Set<Function> seenSet = new HashSet<>();
        addFunc(creator, functionList, seenSet);

        while (!functionList.isEmpty()) {
            Function f = functionList.removeLast();
            //System.out.println("F = " + f.getClass());
            Variable[] inputs = f.getInputs();
            Variable[] gys = new Variable[f.outputs.length];
            for (int i = 0; i < f.outputs.length; i++) {
                if (f.outputs[i].grad == null) {
                    double[] g = new double[f.outputs[i].getLength()];
                    Arrays.fill(g, 1.0);
                    // 20240506
                    // 入力の形状か出力の形状かわからん。多分だけど出力と同じ形状で合ってる気がする
                    gys[i] = new Variable(g, f.getOutput(i).getShape());
                    //gys[i] = new Variable(g, f.inputs[i].getShape());
                } else {
                    //gys[i] = f.outputs[i].grad;
                    gys[i] = (f.getOutput(i).grad.reshape(f.getOutput(i).getShape()));
                }
            }

            try (UsingConfig config = new UsingConfig("enable_backprop", createGraph)) {
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
                            // plusAssignはNG
                            x.grad = x.grad.plus(gx);
                        }

                        if (x.getCreator() != null) {
                            addFunc(x.getCreator(), functionList, seenSet);
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

    private void addFunc(Function f, List<Function> funcs, Set<Function> seenSet) {
        if (!seenSet.contains(f) && f != null) {
            funcs.add(f);
            seenSet.add(f);
            funcs.sort((f1, f2) -> Integer.compare(f1.getGeneration(), f2.getGeneration()));
        }
    }

    public Tensor getData() {
        return data;
    }

    public double[] getValues() {
        return data.values;
    }

    public Variable getGrad() {
        return grad;
    }

    public void setGrad(Variable grad) {
        this.grad = grad;
    }

    public Function getCreator() {
        return creator;
    }

    public void setCreator(Function func) {
        this.creator = func;
        this.generation = func.getGeneration() + 1;
    }

    public Variable plus(Variable other) {
        Function function = new Plus();
        return function.forward(this, other)[0];
    }

    public Variable plus(double other) {
        Function function = new Plus();
        double[] array = new double[getLength()];
        for (int i = 0; i < getLength(); i++) {
            array[i] = other;
        }
        Variable otherVariable = new Variable(array, this.getShape());
        return function.forward(this, otherVariable)[0];
    }

    public void plusAssign(Variable other) {
        data.plusAssign(other.getData());
    }

    public Variable minus(Variable other) {
        Function function = new Minus();
        return function.forward(this, other)[0];
    }

    public Variable rminus(Variable other) {
        Function function = new Minus();
        return function.forward(other, this)[0];
    }

    public void minusAssign(Variable other) {
        data.minusAssign(other.getData());
    }

    public Variable times(Variable other) {
        Function function = new Times();
        return function.forward(this, other)[0];
    }

    public Variable times(double other) {
        Function function = new Times();
        double[] array = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            array[i] = other;
        }
        Variable otherVariable = new Variable(array, getShape());
        //Variable otherVariable = new Variable(array);
        return function.forward(this, otherVariable)[0];
    }

    public Variable div(Variable other) {
        Function function = new Div();
        return function.forward(this, other)[0];
    }

    public Variable rdiv(Variable other) {
        Function function = new Div();
        return function.forward(other, this)[0];
    }

    public Variable neg() {
        Function function = new Neg();
        return function.forward(this)[0];
    }

    public Variable pow(double index) {
        Power function = new Power();
        function.setIndex(index);
        return function.forward(this)[0];
    }

    public Variable square() {
        Function function = new Square();
        return function.forward(this)[0];
    }

    public Variable sin() {
        Function function = new Sin();
        return function.forward(this)[0];
    }

    public Variable cos() {
        Function function = new Cos();
        return function.forward(this)[0];
    }

    public Variable tanh() {
        Function function = new Tanh();
        return function.forward(this)[0];
    }

    public Variable reshape(int[] shape) {
        Function function = new Reshape(shape);
        return function.forward(this)[0];
    }

    public Variable transpose() {
        Function function = new Transpose();
        return function.forward(this)[0];
    }

    public Variable broadcastTo(int[] shape) {
        Function function = new BroadcastTo(shape);
        return function.forward(this)[0];
    }

    public Variable sumTo(int[] shape) {
        Function function = new SumTo(shape);
        return function.forward(this)[0];
    }

    public Variable sum() {
        Function function = new Sum();
        return function.forward(this)[0];
    }

    public Variable sum(int axis) {
        Function function = new Sum(axis);
        return function.forward(this)[0];
    }

    public Variable dot(Variable other) {
        Function function = new Dot();
        return function.forward(this, other)[0];
    }

    public Variable mse(Variable other) {
        Function function = new MeanSquaredError();
        return function.forward(this, other)[0];
    }

    public Variable sigmoid() {
        Function function = new Sigmoid();
        return function.forward(this)[0];
    }

    public Variable linear(Variable w, Variable b) {
        Function function = new Linear();
        return function.forward(this, w, b)[0];
    }

    public String toString() {
        return data.toString();
    }

    @Override
    public Variable clone() {
        try {
            Variable clone = (Variable) super.clone();
            // TODO: このクローンが元の内部を変更できないようにミュータブルな状態をここにコピーします
            if(data != null) {
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

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        //xs[0] = new Variable(new double[]{2});
        xs[0] = new Variable(new double[]{1, 2, 3});
        Variable x = xs[0];
        Fx fx = new Fx();
        int maxIteration = 10;

        for (int i = 0; i < maxIteration; i++) {
            Variable y = fx.forward(xs)[0];
            x.clearGrad();
            y.backward(true, true);
            Variable gxs = x.getGrad();
            System.out.print(x + "  " + y + " " + gxs + " ");
            x.clearGrad();
            gxs.backward(false, true);
            Variable gxs2 = x.getGrad();

            System.out.print(gxs2.toString() + "\n");
            /*
            for (int j = 0; j < x.getData().length; j++) {
                xs[0].getDataAsArray()[j] -= gxs.getDataAsArray()[j] / gxs2.getDataAsArray()[j];
            }

             */
            xs[0].minusAssign(gxs.div(gxs2));

        }
    }

}
