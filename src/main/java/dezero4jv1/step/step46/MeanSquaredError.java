package dezero4jv1.step.step46;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class MeanSquaredError extends Function {

    public MeanSquaredError() {
        numInputs = 2;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = (((xs[0].minus(xs[1])).pow(2)).sum()).div(new Tensor(xs[0].getLength()));
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable dx = inputs[0].minus(inputs[1]);
        Variable gy = gys[0].broadcastTo(dx.getShape());
        gxs[0] = gy.times(dx).times(new Variable(2.0 / dx.getLength(), inputs[0].getShape()));
        gxs[1] = gxs[0].neg();
        return gxs;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[]{1, 3, -3});
        xs[1] = new Variable(new double[]{4, 5, 6});
        System.out.println("x");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        Function mse = new MeanSquaredError();
        Variable y = mse.forward(xs)[0];
        System.out.println("y");
        System.out.println(y);
        xs[0].clearGrad();
        xs[1].clearGrad();
        y.backward(false, true);
        System.out.println("x");
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
    }
}
