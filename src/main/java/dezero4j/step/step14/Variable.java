package dezero4j.step.step14;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    private double[] data;
    private double[] grad;
    private AbstractFunction creator;

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

    public void setCreator(AbstractFunction func) {
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
        ArrayList<AbstractFunction> funcList = new ArrayList<>();
        funcList.add(creator);
        while (!funcList.isEmpty()) {
            AbstractFunction f = funcList.remove(funcList.size() - 1);
            double[][] gys = new double[f.outputs.length][];
            for (int i = 0; i < f.outputs.length; i++) {
                gys[i] = f.outputs[i].grad;
            }
            double[][] gxs = f.backward(gys);
            if (gxs.length != f.inputs.length) {
                throw new IllegalStateException("Length of gradients and inputs do not match");
            }
            for (int i = 0; i < gxs.length; i++) {
                //f.inputs[i].grad += gxs[i];
                if (f.inputs[i].grad == null) {
                    //f.inputs[i].grad = new double[data.length];
                    f.inputs[i].grad = gxs[i];
                    //Arrays.fill(f.inputs[i].grad, 1.0);
                } else {
                    for (int j = 0; j < gxs[i].length; j++) {
                        f.inputs[i].grad[j] += gxs[i][j];
                    }
                }
                /*
                f.inputs[i].setGrad(gxs[i]);
                 */

                if (f.inputs[i].creator != null) {
                    funcList.add(f.inputs[i].creator);
                }
            }
        }
        /*
        zawashin.dezero4j.step162.AbstractFunction[] funcs = {creator};
        while (funcs.length > 0) {
            zawashin.dezero4j.step162.AbstractFunction f = funcs[funcs.length - 1];
            funcs = Arrays.copyOfRange(funcs, 0, funcs.length - 1);
            double[][] gys = new double[f.outputs.length][];
            for (int i = 0; i < f.outputs.length; i++) {
                gys[i] = f.outputs[i].getGrad();
            }
            double[][] gxs = f.backward(gys);
            if (gxs.length != f.inputs.length) {
                throw new IllegalStateException("Number of gradients does not match number of inputs");
            }
            for (int i = 0; i < gxs.length; i++) {
                f.inputs[i].setGrad(gxs[i]);
                if (f.inputs[i].creator != null) {
                    funcs = Arrays.copyOf(funcs, funcs.length + 1);
                    funcs[funcs.length - 1] = f.inputs[i].creator;
                }
            }
        }

         */
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

    public AbstractFunction getCreator() {
        return creator;
    }

    Variable[] plus(Variable other) {
        AbstractFunction plus = new Plus();
        return plus.forward(this, other);
    }
    Variable[] square() {
        AbstractFunction square = new Square();
        return square.forward(this);
    }
}
