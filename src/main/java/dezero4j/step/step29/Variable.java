package dezero4j.step.step29;

import java.util.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable implements Cloneable {
    private final double[] data;
    private double[] grad;
    private Function creator;
    private int generation;
    private final int rank;
    private final int[] shape;
    private final int length;

    public Variable(double data) {
        this.data = new double[]{data};
        this.grad = null;
        this.creator = null;
        generation = 0;
        rank = 0;
        shape = new int[1];
        shape[0] = 1;
        length = 1;
    }

    public Variable(int length, double data) {
        if (length == 1) {
            rank = 0;
        } else {
            rank = 1;
        }
        this.length = length;
        this.data = new double[length];
        for (int i = 0; i < length; i++) {
            this.data[i] = data;
        }
        this.grad = null;
        this.creator = null;
        generation = 0;
        shape = new int[1];
        shape[0] = length;
    }

    public Variable(double[] data) {
        if (data.length > 1) {
            rank = 1;
            this.data = data;
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
        length = data.length;
    }

    public Variable(double[][] data) {
        rank = 2;
        shape = new int[2];
        shape[0] = data.length;
        shape[1] = data[0].length;
        this.data = new double[shape[0] * shape[1]];
        int n = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                this.data[n] = data[i][j];
                n++;
            }
        }
        this.grad = null;
        this.creator = null;
        generation = 0;
        length = data.length;
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

    public int getLength() {
        return length;
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

    public Variable plus(Variable other) {
        Function function = new Plus();
        return function.fprward(this, other)[0];
    }

    public Variable plus(double other) {
        Function function = new Plus();
        double[] array = generateArray(other);
        Variable otherVariable = new Variable(array);
        return function.fprward(this, otherVariable)[0];
    }

    public Variable minus(Variable other) {
        Function function = new Minus();
        return function.fprward(this, other)[0];
    }

    public Variable rminus(Variable other) {
        Function function = new Minus();
        return function.fprward(other, this)[0];
    }

    public Variable times(Variable other) {
        Function function = new Times();
        return function.fprward(this, other)[0];
    }

    public Variable times(double other) {
        Function function = new Times();
        double[] array = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            array[i] = other;
        }
        Variable otherVariable = new Variable(array);
        return function.fprward(this, otherVariable)[0];
    }

    public Variable div(Variable other) {
        Function function = new Div();
        return function.fprward(this, other)[0];
    }

    public Variable rdiv(Variable other) {
        Function function = new Div();
        return function.fprward(other, this)[0];
    }

    public Variable negative() {
        Function function = new Negative();
        return function.fprward(this)[0];
    }

    public Variable pow(double index) {
        Function function = new Power();
        ((Power) function).setIndex(index);
        return function.fprward(this)[0];
    }

    public Variable square() {
        Function function = new Square();
        return function.fprward(this)[0];
    }

    public Variable sin() {
        Function function = new Sin();
        return function.fprward(this)[0];
    }

    public String toString() {
        if (rank < 2) {
            return Arrays.toString(data);
        } else if (rank == 2) {
            StringBuilder sb = new StringBuilder();

            int height = shape[0];
            int width = shape[1];
            double[][] matrix = new double[shape[0]][shape[1]];
            int n = 0;
            sb.append("[");
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    matrix[i][j] = data[n];
                    n++;
                }
                sb.append(Arrays.toString(matrix[i]));
                if (i <= height - 1) {
                    sb.append("," + "\n");
                }
            }
            sb.append("]");
            return sb.toString();
        } else {
            return "Not implemented";
        }
    }

    protected double[] generateArray(double value) {
        double[] array = new double[length];
        Arrays.fill(array, value);
        return array;
    }

    protected double[] generateArray() {
        double[] array = new double[length];
        Arrays.fill(array, 0.0);
        return array;
    }

}
