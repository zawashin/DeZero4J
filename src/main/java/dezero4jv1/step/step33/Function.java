package dezero4jv1.step.step33;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function {
    protected Variable[] inputs;
    protected Variable[] outputs;
    protected int generation;
    protected int numInputs;
    protected int numOutputs;
    protected int length;

    public Variable[] getInputs() {
        return inputs;
    }

    public Variable[] getOutputs() {
        return outputs;
    }

    public int getGeneration() {
        return generation;
    }

    public Variable param(int length, double data) {
        return new Variable(length, data);
    }

    public Variable param(double data) {
        return new Variable(length, data);
    }

    public abstract double[][] forward(double[]... xs);

    public Variable[] forward(Variable... xs) {
        length = inputs[0].length;
        double[][] xsArray = new double[inputs.length][];
        for (int i = 0; i < inputs.length; i++) {
            xsArray[i] = inputs[i].getData();
        }
        if (Config.getInstance().getParam().get("enable_backprop")) {
            generation = Arrays.stream(inputs).mapToInt(Variable::getGeneration).max().orElse(0);

            double[][] ysArray = forward(xsArray);
            Variable[] outputs = new Variable[ysArray.length];
            for (int i = 0; i < ysArray.length; i++) {
                outputs[i] = new Variable(ysArray[i]);
                outputs[i].setCreator(this);
            }

            this.inputs = inputs;
            this.outputs = outputs;
        }
        return outputs;
    }

    public abstract Variable[] backward(Variable... gys);

}
