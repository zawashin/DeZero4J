package dezero4jv1.step.step18;

import java.util.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    protected double[] data;
    protected double[] grad;
    protected Function creator;
    protected int generation;

    public Variable(double[] data) {
        this.data = data;
        this.grad = null;
        this.creator = null;
        this.generation = 0;
    }

    public Variable(double data) {
        this.data = new double[]{data};
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

    Variable[] plus(Variable other) {
        Function plus = new Plus();
        return plus.forward(this, other);
    }

    Variable[] square() {
        Function square = new Square();
        return square.forward(this);
    }
}
