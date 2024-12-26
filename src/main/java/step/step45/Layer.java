package step.step45;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Layer implements Serializable {
    protected int numInputs;
    protected List<Integer> numOutputs;
    protected List<Parameter> params;
    protected boolean initialized = false;
    protected Function function;

    {
        numOutputs = new ArrayList<>();
        params = new ArrayList<>();
    }

    public Layer(int numOutputs) {
        if (numOutputs <= 0) {
            throw new IllegalArgumentException("numOutputs must be greater than to 0.");
        }
        this.numOutputs.add(numOutputs);
    }

    public Layer(int numOutputs, int numInputs) {
        this.numInputs = numInputs;
        this.numOutputs.add(numOutputs);
    }

    public abstract Variable[] forward(Variable... inputs);

    public void update(double learningRate) {
        for (Variable param : params) {
            param.subtractAssign((param.grad).multiply(learningRate));
        }
    }

    public void clearGrads() {
        for (Variable param : params) {
            param.clearGrad();
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
