package dezero4j.step.step20;

import java.util.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    protected double[] data;
    protected double[] grad;
    protected Function creator;
    protected int generation;
    protected final int rank;
    protected final int[] shape;

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

    public void setCreator(Function func) {
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
        ArrayList<Function> funcs = new ArrayList<>();
        funcs.add(creator);
        Set<Function> seenSet = new HashSet<>();
        seenSet.add(creator);

        while (!funcs.isEmpty()) {
            Function f = funcs.remove(funcs.size() - 1);
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
                        funcs.sort(Comparator.comparingInt(Function::getGeneration));
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

    public Function getCreator() {
        return creator;
    }

    public Variable[] plus(Variable other) {
        Function function = new Plus();
        return function.forward(this, other);
    }

    public Variable[] times(Variable other) {
        Function function = new Times();
        return function.forward(this, other);
    }

    public Variable[] square() {
        Function function = new Square();
        return function.forward(this);
    }
}
