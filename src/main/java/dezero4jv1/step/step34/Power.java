package dezero4jv1.step.step34;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Power extends Function {
    private double index = 0;

    public Power() {
        numInputs = 1;
        numOutputs = 1;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public double[][] forward(double[]... xs) {
        double[] x0 = xs[0];
        double[][] ys = new double[1][xs[0].length];
        for (int i = 0; i < x0.length; i++) {
            ys[0][i] = Math.pow(x0[i], index);
        }
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[1];
        Variable x = inputs[0];
        int length = x.length;
        gx[0] = (x.pow(index - 1)).times(param(length, index)).times(gys[0]);
        return gx;
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
        System.out.println(x.getData()[0] + " " + y.getData()[0] + " " + dx.getData()[0] + " " + dx2.getData()[0]);
        System.out.println(x.getData()[1] + " " + y.getData()[1] + " " + dx.getData()[1] + " " + dx2.getData()[1]);
        System.out.println(x.getData()[2] + " " + y.getData()[2] + " " + dx.getData()[2] + " " + dx2.getData()[2]);
    }
}
