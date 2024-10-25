package dezero4jv1.step.step39;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Power extends Function {
    private double index = 0;

    public Power() {
        numInputs = 1;
        numOutputs = 1;

    }

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{1.0, 2.0, 3.0});
        Power pow = new Power();
        pow.setIndex(4.0);
        Variable y = pow.forward(x)[0];
        x.clearGrad();
        y.backward(true, true);
        Variable dx = x.getGrad();
        dx.clearGrad();
        dx.backward(false, true);
        Variable dx2 = x.getGrad();
        System.out.println(x.getDataAsArray()[0] + " " + y.getDataAsArray()[0] + " " + dx.getDataAsArray()[0] + " " + dx2.getDataAsArray()[0]);
        System.out.println(x.getDataAsArray()[1] + " " + y.getDataAsArray()[1] + " " + dx.getDataAsArray()[1] + " " + dx2.getDataAsArray()[1]);
        System.out.println(x.getDataAsArray()[2] + " " + y.getDataAsArray()[2] + " " + dx.getDataAsArray()[2] + " " + dx2.getDataAsArray()[2]);
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        double[] x0 = xs[0].values;
        int length = xs[0].length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.pow(x0[i], index);
        }
        Tensor[] ys = new Tensor[1];
        ys[0] = new Tensor(values, xs[0].shape);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[1];
        Variable x = inputs[0];
        int length = x.getLength();
        gx[0] = (x.pow(index - 1)).times(param(length, index)).times(gys[0]);
        gx[0].setShape(inputs[0].getShape());

        return gx;
    }
}
