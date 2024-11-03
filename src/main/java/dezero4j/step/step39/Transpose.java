package dezero4j.step.step39;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Transpose extends Function {

    int[] axes;

    public Transpose(int... axes) {
        this.axes = axes;
        if (axes.length == 1 || axes.length > 2) {
            throw new RuntimeException("# of Axes must be 0 or 2");
        }
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        if (xs[0].getRank() <= 2) {
            return new Tensor[]{Utils.transpose(xs[0])};
        } else {
            return new Tensor[]{Utils.transpose(xs[0], axes[0], axes[1])};
        }
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Tensor gx;
        if (gys[0].getRank() <= 2) {
            gx = Utils.transpose(gys[0].getData());
        } else {
            gx = Utils.transpose(gys[0].getData(), axes[1], axes[0]);
        }
        return new Variable[]{new Variable(gx)};
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x = new Variable(new double[]{4});
        Variable y = x.transpose();
        System.out.println("xT = " + y);
        x = new Variable(new double[]{1, 4});
        System.out.println(x);
        System.out.println(Arrays.toString(x.getShape()));
        y = x.transpose();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println(x);
        y = x.transpose();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
    }
}
