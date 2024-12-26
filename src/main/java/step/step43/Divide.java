package step.step43;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Divide extends Function {
    boolean broadcast = false;
    int[] shape;

    public static void main(String[] args) {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 8}});
        xs[1] = new Variable(3);
        Variable y = xs[0].divide(xs[1]);
        y.backward(false, true);
        System.out.println("x:[2nd]/[0th]");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        System.out.println(y);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 7}});
        xs[1] = new Variable(new double[]{1, 2, 4});
        y = xs[0].divide(xs[1]);
        y.backward(false, true);
        System.out.println("x:[2nd]/[1st]");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        System.out.println(y);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);

        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 8}});
        xs[1] = new Variable(new double[][]{{1, 2, 4}, {1, 2, 4}});
        y = xs[0].divide(xs[1]);
        y.backward(false, true);
        System.out.println("x");
        System.out.println("x:[2nd]/[2nd]");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
        /*
         */
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] xs_ = new Tensor[2];
        if (!Arrays.equals(xs[0].getShape(), xs[1].getShape())) {
            broadcast = true;
            shape = Utils.broadcastShape(xs[0].getShape(), xs[1].getShape());
            xs_[0] = xs[0].broadcastTo(shape);
            xs_[1] = xs[1].broadcastTo(shape);

        } else {
            xs_[0] = xs[0];
            xs_[1] = xs[1];
        }
        return new Tensor[]{xs_[0].divide(xs_[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[2];
        Variable[] xs = new Variable[2];
        if (broadcast) {
            xs[0] = inputs[0].broadcastTo(shape);
            xs[1] = inputs[1].broadcastTo(shape);
            gxs[0] = gys[0].divide(xs[1]);
            gxs[1] = gys[0].neg().multiply(xs[0].divide(xs[1].square()));
            gxs[0] = gxs[0].sumTo(inputs[0].getShape());
            gxs[1] = gxs[1].sumTo(inputs[1].getShape());
        } else {
            xs[0] = inputs[0];
            xs[1] = inputs[1];
            gxs[0] = gys[0].divide(xs[1]);
            gxs[1] = gys[0].neg().multiply(xs[0].divide(xs[1].square()));
        }

        return gxs;
    }
}
