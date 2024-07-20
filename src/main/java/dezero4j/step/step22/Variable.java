package dezero4j.step.step22;

import dlfs3.step.step22.Divide;
import dlfs3.step.step22.Function;
import dlfs3.step.step22.Minus;
import dlfs3.step.step22.Multiply;
import dlfs3.step.step22.Negative;
import dlfs3.step.step22.Plus;
import dlfs3.step.step22.Power;
import dlfs3.step.step22.Square;

import java.util.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    private double[] data;
    private double[] grad;
    private dlfs3.step.step22.Function creator;
    private int generation;
    private final int rank;
    private final int[] shape;

    public Variable(double data) {
        this.data = new double[]{data};
        this.grad = null;
        this.creator = null;
        generation = 0;
        rank = 0;
        shape = new int[1];
        shape[0] = 1;
    }

    public Variable(double[] data) {
        if(data.length > 1) {
            this.data = data;
            rank = 1;
        } else {
            rank = 0;
            this.data = new double[1];
            this.data[0] = data[0];
        }
        this.grad = null;
        this.creator = null;
        generation = 0;
        shape = new int[1];
        shape[0] = data.length;
    }

    public Variable(double[][] data) {
        rank = 2;
        shape = new int[2];
        shape[0] = data.length;
        shape[1] = data[0].length;
        this.data = new double[shape[0] * shape[1]];
        int n = 0;
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                this.data[n] = data[i][j];
                n++;
            }
        }
        this.grad = null;
        this.creator = null;
        generation = 0;
    }

    public int getGeneration() {
        return generation;
    }

    public void setCreator(dlfs3.step.step22.Function func) {
        this.creator = func;
        this.generation = func.getGeneration() + 1;
    }

    public int getRank() {
        return rank;
    }

    public int[] getShape() {
        return shape;
    }

    public void cleaGrad() {
        grad = null;
    }

    public void backward() {
        if (grad == null) {
            grad = new double[data.length];
            Arrays.fill(grad, 1.0);
        }
        ArrayList<dlfs3.step.step22.Function> funcs = new ArrayList<>();
        funcs.add(creator);
        Set<dlfs3.step.step22.Function> seenSet = new HashSet<>();
        seenSet.add(creator);

        while (!funcs.isEmpty()) {
            dlfs3.step.step22.Function f = funcs.remove(funcs.size() - 1);
            double[][] gys = new double[f.outputs.length][];
            for (int i = 0; i < f.outputs.length; i++) {
                gys[i] = f.outputs[i].grad;
            }
            double[][] gxs = f.backward(gys);
            if (gxs.length != f.inputs.length) {
                throw new IllegalStateException("Length of gradients and inputs do not match");
            }

            for (int i = 0; i < gxs.length; i++) {
                Variable x = f.inputs[i];
                //f.inputs[i].grad += gxs[i];
                if (f.inputs[i].grad == null) {
                    f.inputs[i].grad = gxs[i];
                } else {
                    for (int j = 0; j < gxs[i].length; j++) {
                        f.inputs[i].grad[j] += gxs[i][j];
                    }
                }

                if (x.getCreator() != null) {
                    if (!seenSet.contains(x.creator)) {
                        funcs.add(x.creator);
                        seenSet.add(x.creator);
                        funcs.sort(Comparator.comparingInt(dlfs3.step.step22.Function::getGeneration));
                    }
                }
            }
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

    public dlfs3.step.step22.Function getCreator() {
        return creator;
    }

    Variable plus(Variable other) {
        dlfs3.step.step22.Function function = new Plus();
        return function.forward(this, other)[0];
    }

    Variable minus(Variable other) {
        dlfs3.step.step22.Function function = new dlfs3.step.step22.Minus();
        return function.forward(this, other)[0];
    }

    Variable rminus(Variable other) {
        dlfs3.step.step22.Function function = new Minus();
        return function.forward(other, this)[0];
    }

    Variable multiply(Variable other) {
        dlfs3.step.step22.Function function = new Multiply();
        return function.forward(this, other)[0];
    }

    Variable divide(Variable other) {
        dlfs3.step.step22.Function function = new dlfs3.step.step22.Divide();
        return function.forward(this, other)[0];
    }

    Variable rdivide(Variable other) {
        dlfs3.step.step22.Function function = new Divide();
        return function.forward(other, this)[0];
    }

    Variable negative() {
        dlfs3.step.step22.Function function = new Negative();
        return function.forward(this)[0];
    }

    Variable pow(double index) {
        dlfs3.step.step22.Function function = new dlfs3.step.step22.Power();
        ((Power)function).setIndex(index);
        return function.forward(this)[0];
    }

    Variable square() {
        Function function = new Square();
        return function.forward(this)[0];
    }
}
