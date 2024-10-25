package dezero4jv1.step.step40;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Transpose extends Function {

    public Transpose() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorOperator.transpose(xs[0]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0].transpose();
        gxs[0].setShape(inputs[0].getShape());
        return gxs;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x = new Variable(new double[]{4});
        Variable y = x.transpose();
        System.out.println(y);
        x = new Variable(new double[]{1, 4});
        System.out.println(x);
        System.out.println(Arrays.toString(x.getShape()));
        y = x.transpose();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        y = x.transpose();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
    }
}
