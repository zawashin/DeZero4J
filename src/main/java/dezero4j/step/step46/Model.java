package dezero4j.step.step46;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Model extends Layer {

    protected List<Layer> layers = new ArrayList<>();
    protected List<ActivationFunction> activates = new ArrayList<>();
    protected Variable[][] ys;

    public Model(int... numOutputs) {
        super(numOutputs[0]);
        for (int numOutput : numOutputs) {
            this.numOutputs.add(numOutput);
            addLayer(new Affine(numOutput));
        }
    }

    /*

    public Model(Layer... layers) {
        super(layers.length);
        this.layers.addAll(Arrays.asList(layers));
    }
     */

    public void addLayer(Layer layer) {
        layers.add(layer);
        activates.add(new Sigmoid());
    }

    public void addLayer(int numOutputs) {
        layers.add(new Affine(numOutputs));
        activates.add(new Sigmoid());
    }

    public void addLayer(int n, Layer layer) {
        layers.add(n, layer);
        activates.add(new Sigmoid());
    }

    public void addLayer(int numOutputs, ActivationFunction function) {
        layers.add(new Affine(numOutputs));
        activates.add(function);
    }

    public void addLayer(int n, Layer layer, ActivationFunction function) {
        layers.add(n, layer);
        activates.add(function);
    }

    public void addLayer(Layer layer, ActivationFunction function) {
        layers.add(layer);
        activates.add(function);
    }

    public void clearGrads() {
        for (Layer layer : layers) {
            layer.clearGrads();
        }
    }

    public void update(double learningRate) {
        for (Layer layer : layers) {
            layer.update(learningRate);
        }
    }

    public void addParam(Layer layer) {
        params.addAll(layer.params);
    }

    @Override
    public Variable[] forward(Variable[] xs) {
        ys = new Variable[2 * layers.size()][];
        int n = layers.size() - 1;
        ys[0] = xs;
        for (int i = 0; i < n; i++) {
            ys[2 * i + 1] = layers.get(i).forward(ys[2 * i]);
            ys[2 * i + 2] = activates.get(i).forward(ys[2 * i + 1]);
        }
        ys[2 * n + 1] = layers.getLast().forward(ys[2 * n]);
        if (!initialized) {
            for (Layer layer : layers) {
                params.addAll(layer.params);
            }
            initialized = true;
        }

        return ys[2 * n + 1];
    }

    public Variable predict(Variable[] xs) {
        return forward(xs)[0];
    }

}
