package dezero4j.step.step37_weakreference;

import tensor4j.Tensor;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */

public abstract class Function {

    protected int generation;
    protected Variable[] inputs;
    protected List<WeakReference<Variable>> outputs;


    public Variable[] forward(Variable... inputs) {
        this.inputs = inputs;
        this.outputs = new ArrayList<>();

        Tensor[] xs = new Tensor[inputs.length];
        for (int i = 0; i < xs.length; i++) {
            xs[i] = inputs[i].getData();
        }
        Tensor[] ys = forward(xs);
        for (Tensor y : ys) {
            outputs.add(new WeakReference<>(new Variable(y)));
        }

        if (Config.enableBackprop) {
            generation = Arrays.stream(inputs).mapToInt(Variable::getGeneration).max().orElse(0);
            for (int i = 0; i < ys.length; i++) {
                getOutput(i).setCreator(this);
                outputs.add(new WeakReference<>(getOutput(i)));
            }
        }

        return getOutputs();
    }

    protected abstract Tensor[] forward(Tensor... xs);

    protected abstract Variable[] backward(Variable... gys);

    public Variable[] getInputs() {
        return inputs;
    }

    public Variable getInput(int n) {
        return inputs[n];
    }

    public Variable[] getOutputs() {
        Variable[] ys = new Variable[outputs.size()];
        for (int i = 0; i < ys.length; i++) {
            WeakReference<Variable> weakRef = outputs.get(i);
            ys[i] = weakRef.get();
        }
        return ys;
    }

    public Variable getOutput(int n) {
        WeakReference<Variable> weakRef = outputs.get(n);
        Variable y = weakRef.get();

        // y を使って処理を行う
        // y がガベージコレクションで解放されている場合の処理
        return y;
    }

    public int getGeneration() {
        return generation;
    }
}

