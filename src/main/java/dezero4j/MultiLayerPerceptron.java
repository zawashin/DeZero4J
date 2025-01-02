package dezero4j;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class MultiLayerPerceptron extends Model {

    @Serial
    private static final long serialVersionUID = -135745206154451495L;

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
