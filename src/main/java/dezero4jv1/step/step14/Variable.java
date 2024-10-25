package dezero4jv1.step.step14;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    private final double[] data;
    private double[] grad;
    private Function creator;

    public Variable(double[] data) {
        this.data = data;
        this.grad = null;
        this.creator = null;
    }

    public Variable(double data) {
        this.data = new double[]{data};
        this.grad = null;
        this.creator = null;
    }

    public void setCreator(Function func) {
        this.creator = func;
    }

    public void cleaGrad() {
        grad = null;
    }

    public void backward() {
        if (grad == null) {
            grad = new double[data.length];
            Arrays.fill(grad, 1.0);
        }
        ArrayList<Function> funcList = new ArrayList<>();
        funcList.add(creator);
        while (!funcList.isEmpty()) {
            Function f = funcList.remove(funcList.size() - 1);
            double[][] gys = new double[f.outputs.length][];
            for (int i = 0; i < f.outputs.length; i++) {
                gys[i] = f.outputs[i].grad;
            }
            double[][] gxs = f.backward(gys);
            if (gxs.length != f.inputs.length) {
                throw new IllegalStateException("Length of gradients and inputs do not match");
            }
            for (int i = 0; i < gxs.length; i++) {
                if (f.inputs[i].grad == null) {
                    f.inputs[i].grad = gxs[i];
                } else {
                    for (int j = 0; j < gxs[i].length; j++) {
                        f.inputs[i].grad[j] += gxs[i][j];
                    }
                }

                if (f.inputs[i].creator != null) {
                    funcList.add(f.inputs[i].creator);
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
