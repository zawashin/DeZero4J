package dezero4j.step.step39;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Multiply extends Function {

    public static void main(String[] args) {
        {
            Variable[] xs = new Variable[2];
            xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
            xs[1] = new Variable(new double[][]{{4, 5, 6}, {1, 2, 3}});
            System.out.println("x");
            System.out.println(xs[0]);
            System.out.println(xs[1]);
            Variable y = xs[0].multiply(xs[1]);
            System.out.println(y);
            y.backward(false, true);
            System.out.println(xs[0].grad);
            System.out.println(xs[1].grad);
        }
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].multiply(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        /*
        Tensor[] xs = new Tensor[]{inputs[0].getData(), inputs[1].getData()};
        return new Tensor[]{xs[1].multiply(gys[0]), xs[0].multiply(gys[0])};

         */
        Variable[] xs = inputs;
        return new Variable[]{xs[1].multiply(gys[0]), xs[0].multiply(gys[0])};
    }
}
