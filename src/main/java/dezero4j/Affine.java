package dezero4j;


import tensor4j.TensorUtils;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Affine extends Layer {

    @Serial
    private static final long serialVersionUID = -1779198916426024263L;
    boolean noBias = false;

    public Affine(int numOutputs) {
        super(numOutputs);
        function = new Linear();
    }

    public Affine(int numOutputs, int numInputs) {
        this(numInputs, false, numOutputs);
    }

    public Affine(int numOutputs, boolean noBias, int numInputs) {
        super(numOutputs, numInputs);
        if (!noBias) {
            params.add(new Parameter(TensorUtils.random(numInputs, numOutputs)));
            params.add(new Parameter(new double[numOutputs]));
        } else {
            params.add(new Parameter(TensorUtils.random(numInputs, numOutputs)));
        }
        initialized = true;
        this.noBias = noBias;
        function = new Linear();
    }

    public Variable[] forward(Variable[] xs) {
        if (!initialized) {
            numInputs = xs[0].getShape()[1];
            if (!noBias) {
                params.add(new Parameter(TensorUtils.random(numInputs, numOutputs.getFirst())));
                params.add(new Parameter(new double[numOutputs.getFirst()]));
            } else {
                params.add(new Parameter(TensorUtils.random(numInputs, numOutputs.getFirst())));
            }
            initialized = true;
        }
        if (!noBias) {
            return function.forward(xs[0], params.getFirst(), params.getLast());
        } else {
            return function.forward(xs[0], params.getFirst());
        }
    }

}
