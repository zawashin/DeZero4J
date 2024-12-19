package dezero4j.step.step40;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Multiply extends Function {

    boolean broadcast = false;

    @Override
    public Tensor[] forward(Tensor... xs) {

        Tensor[] xs_ = new Tensor[2];
        if (!Arrays.equals(xs[0].getShape(), xs[1].getShape())) {
            broadcast = true;
            int[] shape_ = Utils.broadcastShape(xs[0].getShape(), xs[1].getShape());
            xs_[0] = xs[0].broadcastTo(shape_);
            xs_[1] = xs[1].broadcastTo(shape_);

        } else {
            xs_[0] = xs[0];
            xs_[1] = xs[1];
        }
        return new Tensor[]{xs_[0].multiply(xs_[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gys_ = new Variable[2];
        if (broadcast) {
            gys_[0] = gys[0].sumTo(inputs[0].getShape());
            gys_[1] = gys[0].sumTo(inputs[1].getShape());
        } else {
            gys_[0] = gys[0].clone();
            gys_[1] = gys[0].clone();
        }
        Variable[] xs = inputs;
        return new Variable[]{xs[1].multiply(gys_[0]), xs[0].multiply(gys_[0])};
    }

    public static void main(String[] args) {
        {
            Variable[] xs = new Variable[2];
            xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
            xs[1] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
            System.out.println("x");
            System.out.println(xs[0]);
            System.out.println(xs[1]);
            Variable y = xs[0].multiply(xs[1]);
            System.out.println(y);
            y.backward(false, true);
            System.out.println(xs[0].grad);
            System.out.println(xs[1].grad);
        }
        {
            Variable[] xs = new Variable[2];
            xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
            xs[1] = new Variable(3);
            System.out.println("x");
            System.out.println(xs[0]);
            System.out.println(xs[1]);

            Variable y = xs[0].multiply(xs[1]);
            System.out.println(y);
            y.backward(false, true);
            System.out.println(xs[0].grad);
            System.out.println(xs[1].grad);
        }
        {
            Variable[] xs = new Variable[2];
            xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
            xs[1] = new Variable(new double[]{1, 2, 4});
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
}
