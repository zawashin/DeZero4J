package step.step42;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class MeanSquaredError extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{(((xs[0].subtract(xs[1])).pow(2)).sum()).divide(new Tensor(xs[0].getLength()))};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[2];
        Variable dx = inputs[0].subtract(inputs[1]);
        Variable gy = gys[0].broadcastTo(dx.getShape());
        //gxs[0] = gy.multiply(dx).multiply(new Variable(2.0 / dx.getLength(), inputs[0].getShape()));
        gxs[0] = gy.multiply(dx).multiply(new Variable(2.0 / dx.getLength()));
        gxs[1] = gxs[0].neg();
        return gxs;
    }

    public Variable calc(Variable x0, Variable x1) {
        return new Variable(forward(x0.data, x1.data)[0]);
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[]{1, 2, 3});
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
