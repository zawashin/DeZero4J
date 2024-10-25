package dezero4jv1.step.step19;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function {
    protected Variable[] inputs;
    protected Variable[] outputs;
    protected int generation;

    public Variable[] forward(Variable... inputs) {
        double[][] xs = new double[inputs.length][];
        for (int i = 0; i < inputs.length; i++) {
            xs[i] = inputs[i].getData();
        }
        if(Config.enableBackprop) {
            generation = Arrays.stream(inputs).mapToInt(Variable::getGeneration).max().orElse(0);

            double[][] ys = forward(xs);
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

    public Variable[] getInputs() {
        return inputs;
    }

    public Variable[] getOutputs() {
        return outputs;
    }

    public int getGeneration() {
        return generation;
    }

    public abstract double[][] forward(double[][] xs);

    public abstract double[][] backward(double[][] gys);

}
