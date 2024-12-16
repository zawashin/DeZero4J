package dezero4j.step.step39;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sum extends Function {

    private int axis;
    private int[] xShape;

    public Sum() {
        this(-1);
    }

    public Sum(int axis) {
        this.axis = axis;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        this.xShape = xs[0].getShape();
        Tensor y = Utils.sum(xs[0], axis);
        return new Tensor[]{y};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Tensor gy = Utils.reshapeSumBackward(gys[0].getData(), xShape, axis);
        Variable gx = new Variable(Utils.broadcastTo(gy, xShape));
        return new Variable[]{new Variable(gx)};
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x;
        Variable y;
        Tensor t;
        int n;
        System.out.println("1D");
        double[] vector = new double[5];
        t = new Tensor(vector);
        n = 0;
        for (int i = 0; i < t.getLength(); i++) {
            t.getValues()[n] = n++;
        }
        x = new Variable(t);
        System.out.println(x);
        y = x.sum();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.getGrad());

        double[][] matrix = new double[3][5];
        System.out.println("2D");
        t = new Tensor(matrix);
        n = 0;
        for (int i = 0; i < t.getLength(); i++) {
            t.getValues()[n] = n++;
        }
        x = new Variable(t);
        System.out.println(x);
        y = x.sum();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        System.out.println(x.sum(0));
        y.backward(false, true);
        System.out.println(x.grad);
        System.out.println(x.sum(1));
        y.backward(false, true);
        System.out.println(x.grad);
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        y = x.sum();
        y.backward(false, true);
        System.out.println(x.grad);
    }

}
