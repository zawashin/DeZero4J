package dezero4jv1.step.step43;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function implements Cloneable {
    protected Variable[] inputs;
    protected Variable[] outputs;
    protected int generation;
    protected int numInputs;
    protected int numOutputs;

    public Variable[] getInputs() {
        return inputs;
    }

    public Variable[] getOutputs() {
        return outputs;
    }

    public int getGeneration() {
        return generation;
    }

    /*
    public Tensor parameter(double data, int[] shape) {
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        double[] array = new double[length];
        Arrays.fill(array, data);
        return new Tensor(array, shape);
    }

     */

    public Variable parameter(double data, int length) {
        return new Variable(data, length);
    }

    public Variable parameter(double data, int[] shape) {
        return new Variable(data, shape);
    }

    public abstract Tensor[] forward(Tensor... xs);

    public Variable[] forward(Variable... inputs) {
        Tensor[] xs = new Tensor[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            xs[i] = inputs[i].getData();
        }
        if (Config.getInstance().getParam().get("enable_backprop")) {
            generation = Arrays.stream(inputs).mapToInt(Variable::getGeneration).max().orElse(0);

            Tensor[] ys = forward(xs);
            Variable[] outputs = new Variable[ys.length];
            for (int i = 0; i < ys.length; i++) {
                outputs[i] = new Variable(ys[i]);
                outputs[i].setCreator(this);
            }

            this.inputs = inputs;
            this.outputs = outputs;
        }
        return outputs;
    }

    public abstract Variable[] backward(Variable... gys);

    public Function clone() {
        try {
            Function clone = (Function) super.clone();
            // TODO: このクローンが元の内部を変更できないようにミュータブルな状態をここにコピーします
            if(clone.inputs != null) {
                clone.inputs = inputs.clone();
            }
            if(clone.outputs != null) {
                clone.outputs = outputs.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
