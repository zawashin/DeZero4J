package dezero4jv1.step.step44;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Power extends Function {
    private double index = 0;

    public Power() {
        numInputs = 1;
        numOutputs = 1;
    }

    public Power(int index) {
        this();
        setIndex(index);
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorOperator.pow(xs[0], index);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable x = inputs[0];
        gxs[0] = (x.pow(index - 1)).times(parameter(index, x.getShape())).times(gys[0]);
        gxs[0].setShape(inputs[0].getShape());

        return gxs;
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
        System.out.println(x.getValues()[0] + " " + y.getValues()[0] + " " + dx.getValues()[0] + " " + dx2.getValues()[0]);
        System.out.println(x.getValues()[1] + " " + y.getValues()[1] + " " + dx.getValues()[1] + " " + dx2.getValues()[1]);
        System.out.println(x.getValues()[2] + " " + y.getValues()[2] + " " + dx.getValues()[2] + " " + dx2.getValues()[2]);
    }

}
