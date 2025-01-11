package step.step40;

import tensor4j.Tensor;
import tensor4j.TensorUtils;


/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Reshape extends Function {
    int[] shape;

    public Reshape(int... shape) {
        this.shape = shape.clone();
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x = new Variable(new double[]{1, 2, 3});
        System.out.println(x);
        Variable y = x.reshape(3, 1);
        //Variable y = x.reshape(3);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);

        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println(x);
        y = x.reshape(6); // {1, 6}に自動変換
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();

        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}});
        System.out.println(x);
        y = x.reshape(2, 6);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        y = x.reshape(3, 4);
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{TensorUtils.reshape(xs[0], shape)};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return new Variable[]{gys[0].reshape(inputs[0].getShape())};
    }
}
