package step.step40;

import tensor4j.Tensor;
import tensor4j.TensorUtils;

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

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{TensorUtils.transpose(xs[0])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Tensor gx;
        gx = TensorUtils.transpose(gys[0].getData());
        return new Variable[]{new Variable(gx)};
    }
}
