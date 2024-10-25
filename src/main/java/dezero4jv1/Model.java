package dezero4jv1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Model extends Layer {

    protected List<Layer> layers = new ArrayList<>();
    protected List<ActivationFunction> activates = new ArrayList<>();
    protected Variable[][] ys;

    public Model() {
        super(0);
    }

    public Model(int... numOutputs) {
        super(numOutputs[0]);
        for (int numOutput : numOutputs) {
            this.numOutputs.add(numOutput);
            addLayer(new Affine(numOutput));
        }
    }

    public Model(int numInputs, boolean inputs, int... numOutputs) {
        this(numOutputs);
        if (inputs) {
            this.numInputs = numInputs;
        }
        for (int i = 0; i < numOutputs.length; i++) {
            this.numOutputs.add(numOutputs[i]);
            addLayer(new Affine(numOutputs[i]));
        }
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
        activates.add(new Sigmoid());
    }

    public void addLayer(int numOutput) {
        layers.add(new Affine(numOutput));
        activates.add(new Sigmoid());
    }

    public void addLayer(int numOutput, Layer layer) {
        layers.add(numOutput, layer);
        activates.add(new Sigmoid());
    }

    public void addLayer(int numOutput, ActivationFunction function) {
        layers.add(new Affine(numOutput));
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

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        int n = 100;
        double[] xArray = new double[n];
        double[] yArray = new double[n];
        for (int i = 0; i < xArray.length; i++) {

            xArray[i] = random.nextDouble();
            yArray[i] = Math.sin(2.0 * Math.PI * xArray[i]) + random.nextDouble();
            xArray[i] = (double) i / n;
            yArray[i] = Math.sin(2.0 * Math.PI * xArray[i]);
        }
        Variable[] xs = new Variable[1];
        Variable x = new Variable(TensorUtils.createTransposedTensor(xArray));
        xs[0] = x;
        Variable y0 = new Variable(TensorUtils.createTransposedTensor(yArray));

        int numHiddens = 10;
        int numOutputs = 1;
        Model model = new Model();

        model.addLayer(new Affine(numHiddens));
        model.addLayer(new Affine(numHiddens));
        model.addLayer(new Affine(numOutputs));

        double learningRate = 0.2;
        int iters = 10000;
        //iters = 2;
        for (int i = 0; i < iters; i++) {
            Variable y = model.predict(xs);
            Variable loss = y.mse(y0);
            if (i % 100 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
            }
            model.clearGrads();
            loss.backward(false, true);

            model.update(learningRate);
        }
        Variable y = model.predict(xs);
        for (int i = 0; i < x.getLength(); i++) {
            System.out.println(x.getData().getValues()[i] + "\t" + y.getData().getValues()[i] + "\t" + y0.getData().getValues()[i]);
        }
    }

}
