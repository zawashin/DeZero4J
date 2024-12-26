package dezero4j.step.step45;

import tensor4j.Tensor;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function implements Cloneable, Serializable {
    protected Variable[] inputs;
    protected Variable[] outputs;
    protected int generation;

    public Variable[] forward(Variable... inputs) {
        Tensor[] xs = new Tensor[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            xs[i] = inputs[i].getData();
        }
        if (Config.enableBackprop) {
            generation = Arrays.stream(inputs).mapToInt(Variable::getGeneration).max().orElse(0);
            Tensor[] ys = forward(xs);
            outputs = new Variable[ys.length];
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

    public Variable getInput(int n) {
        return inputs[n];
    }

    public Variable[] getOutputs() {
        return outputs;
    }

    public Variable getOutput(int n) {
        return outputs[n];
    }

    public int getGeneration() {
        return generation;
    }

    public abstract Tensor[] forward(Tensor... xs);

    public abstract Variable[] backward(Variable... gys);

    public Variable[] getInputs() {
        return inputs;
    }


    public Function clone() {
        try {
            Function clone = (Function) super.clone();
            // TODO: このクローンが元の内部を変更できないようにミュータブルな状態をここにコピーします
            if (clone.inputs != null) {
                clone.inputs = inputs;
                //clone.inputs = inputs.clone();
            }
            if (clone.outputs != null) {
                clone.outputs = outputs;
                //clone.outputs = outputs.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
