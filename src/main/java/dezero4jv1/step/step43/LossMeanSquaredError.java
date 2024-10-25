package dezero4jv1.step.step43;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class LossMeanSquaredError extends LossFunction {

    @Override
    public Variable[] forward(Variable... xs) {
        Variable[] ys = new Variable[1];
        ys[0] = (((xs[0].minus(xs[1])).pow(2)).sum()).div(parameter(xs[0].getLength(), new int[]{1, 1}));
        return ys;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[]{1, 2, 3});
        xs[1] = new Variable(new double[]{4, 5, 6});
        System.out.println("x");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        LossFunction mse = new LossMeanSquaredError();
        Variable y = mse.forward(xs)[0];
        System.out.println("y");
        System.out.println(y);
        xs[0].clearGrad();
        xs[1].clearGrad();
        y.backward(true, true);
        System.out.println("x");
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
    }
}
