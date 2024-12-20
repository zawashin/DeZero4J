package dezero4j.step.step39;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Divide extends Function {
    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].divide(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] xs = inputs;
        return new Variable[]{gys[0].divide(xs[0]), gys[0].neg().multiply(xs[0].divide(xs[1].square()))};
    }

    public static void main(String[] args) {
        {
            Variable[] xs = new Variable[2];
            xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
            xs[1] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
            System.out.println("x");
            System.out.println(xs[0]);
            System.out.println(xs[1]);
            Variable y = xs[0].divide(xs[1]);
            y.backward(false, true);
            System.out.println(xs[0].grad);
            System.out.println(xs[1].grad);
        }
    }
}
