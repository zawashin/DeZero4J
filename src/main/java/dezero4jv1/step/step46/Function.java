package dezero4jv1.step.step46;

import java.util.ArrayList;
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

    public Variable getInput(int i) {
        return inputs[i];
    }

    public Variable[] getOutputs() {
        return outputs;
    }

    public Variable getOutput(int i) {
        return outputs[i];
    }

    public int getGeneration() {
        return generation;
    }

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
            ArrayList<Variable> outputsList = new ArrayList<>();
            for (int i = 0; i < ys.length; i++) {
                outputsList.add(new Variable(ys[i]));
                outputsList.get(i).setCreator(this);
            }

            this.inputs = inputs;
            Variable[] array = new Variable[outputsList.size()];
            this.outputs = outputsList.toArray(array);
        }
        return outputs;
    }

    public abstract Variable[] backward(Variable... gys);

    public Function clone() {
        try {
            Function clone = (Function) super.clone();
            // TODO: このクローンが元の内部を変更できないようにミュータブルな状態をここにコピーします
            if(clone.inputs != null) {
                clone.inputs = inputs;
                //clone.inputs = inputs.clone();
            }
            if(clone.outputs != null) {
                clone.outputs = outputs;
                //clone.outputs = outputs.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
