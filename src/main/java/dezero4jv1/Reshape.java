package dezero4jv1;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Reshape extends Function {
    int[] shape;
    {
        shape = new int[TensorUtils.RANK_MAX];
        Arrays.fill(shape, 1);
    }

    public Reshape() {
        numInputs = 1;
        numOutputs = 1;
    }

    public Reshape(int[] shape) {
        this();
        setShape(shape);
    }

    public void setShape(int[] shape) {
        if (shape.length == TensorUtils.RANK_MAX) {
            this.shape = shape.clone();
        } else if(shape.length == 1) {
            // デフォルトで行ベクトルに変換
            this.shape[0] = 1;
            this.shape[1] = shape[0];
        } else if (shape.length > TensorUtils.RANK_MAX) {
            throw new RuntimeException(TensorUtils.ERROR_RANK);
        } else {
            throw new RuntimeException(TensorUtils.ERROR_STUB);
        }
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        if (shape == null) {
            shape = xs[0].getShape();
        }
        ys[0] = TensorOperator.reshape(xs[0], shape);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0].reshape(inputs[0].getShape());
        return gxs;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x = new Variable(new double[]{1, 2, 3});
        System.out.println(x);
        Variable y = x.reshape(new int[]{3, 1});
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);

        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println(x);
        y = x.reshape(new int[]{6}); // {1, 6}に自動変換
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();

        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}, {7,8,9}, {10, 11, 12}});
        System.out.println(x);
        y = x.reshape(new int[]{2, 6});
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        y = x.reshape(new int[]{3, 4});
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);
    }
}
