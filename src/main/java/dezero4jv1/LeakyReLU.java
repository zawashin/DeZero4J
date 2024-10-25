package dezero4jv1;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class LeakyReLU extends ActivationFunction {

    private double slope = 0.2;

    public LeakyReLU() {
        numInputs = 1;
        numOutputs = 1;
    }

    public LeakyReLU(double slope) {
        numInputs = 1;
        numOutputs = 1;
        this.slope = slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        Tensor x = xs[0];
        int length = x.getLength();
        double[] values = new double[length];
        for (int i = 0; i < x.length; i++) {
            if(x.getValues()[i] > 0.0) {
                values[i] = x.getValues()[i];
            } else {
                values[i] = 0;
            }
        }
        ys[0] = new Tensor(values);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];

        double[] values = new double[gys[0].getLength()];
        for (int i = 0; i < values.length; i++) {
            if(inputs[0].getValues()[i] > 0.0) {
                values[i] = gys[0].getValues()[i];
            } else {
                values[i] = slope;
            }
        }
        gxs[0] = new Variable(values);

        return gxs;
    }
}
