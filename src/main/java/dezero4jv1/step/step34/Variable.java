package dezero4jv1.step.step34;

import java.util.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    protected double[] data;
    protected Variable grad;
    protected Function creator;
    protected int generation;
    protected int rank;
    protected int[] shape;
    protected int length;

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
        if (data.length == 1 && data[0].length == 1) {
            rank = 0;
        } else if (data.length > 1 && data[0].length == 1) {
            rank = 1;
        } else {
            rank = 2;
        }
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

    public void clearGrad() {
        grad = null;
    }

    public void backward() {
        backward(false, false);
    }

    public void backward(boolean retainGrad, boolean createGraph) {
        if (grad == null) {
            grad = new Variable(length, 1);
        }
        ArrayList<Function> functionList = new ArrayList<>();
        Set<Function> seenSet = new HashSet<>();
        addFunc(creator, functionList, seenSet);

        while (!functionList.isEmpty()) {
            Function f = functionList.removeLast();
            Variable[] inputs = f.getInputs();
            Variable[] gys = new Variable[f.outputs.length];
            for (int i = 0; i < f.outputs.length; i++) {
                if (f.outputs[i].grad == null) {
                    double[] g = new double[f.outputs[i].length];
                    Arrays.fill(g, 1.0);
                    gys[i] = new Variable(g);
                } else {
                    gys[i] = f.outputs[i].grad;
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

    public double[] getData() {
        return data;
    }

    public Variable getGrad() {
        return grad;
    }

    public double[] getGradData() {
        return grad.data;
    }

    public void setGrad(Variable grad) {
        this.grad = grad;
    }

    public Function getCreator() {
        return creator;
    }

    public Variable plus(Variable other) {
        Function function = new Plus();
        return function.forward(this, other)[0];
    }

    public Variable plus(double other) {
        Function function = new Plus();
        double[] array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = other;
        }
        Variable otherVariable = new Variable(array);
        return function.forward(this, otherVariable)[0];
    }

    public Variable minus(Variable other) {
        Function function = new Minus();
        return function.forward(this, other)[0];
    }

    public Variable rminus(Variable other) {
        Function function = new Minus();
        return function.forward(other, this)[0];
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
        Variable otherVariable = new Variable(array);
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

    public Variable negative() {
        Function function = new Negative();
        return function.forward(this)[0];
    }

    public Variable pow(double index) {
        Power function = new Power();
        function.setIndex(index);
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

    @Override
    public Variable clone() {
        try {
            Variable clone = (Variable) super.clone();
            // TODO: このクローンが元の内部を変更できないようにミュータブルな状態をここにコピーします
            clone.data = data.clone();
            clone.shape = shape.clone();
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
            Variable y = fx.calc(xs);
            x.clearGrad();
            y.backward(true, true);
            Variable gx = x.getGrad();
            System.out.print(x + "  " + y + " " + gx + " ");
            x.clearGrad();
            gx.backward(false, true);
            Variable gx2 = x.getGrad();

            System.out.print(gx2.toString() + "\n");
            for (int j = 0; j < x.getData().length; j++) {
                xs[0].getData()[j] -= gx.getData()[j] / gx2.getData()[j];
            }

        }
    }
}
