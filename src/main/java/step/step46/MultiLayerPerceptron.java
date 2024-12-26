package step.step46;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class MultiLayerPerceptron extends Model {

    public MultiLayerPerceptron(int... numOutputs) {
        super(numOutputs);
    }

    public MultiLayerPerceptron(Layer... layers) {
        super(layers.length);
        for (int i = 0; i < layers.length; i++) {
            addLayer(i, layers[i]);
        }
    }
}
