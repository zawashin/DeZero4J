package dezero4j.step.step39;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sum extends Function {

    private final int axis;
    private int[] xShape;
    private final boolean keepDims;

    public Sum() {
        this(-1, false);
    }

    public Sum(int axis) {
        this(axis, false);
    }

    public Sum(int axis, boolean keepDims) {
        this.axis = axis;
        this.keepDims = keepDims;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        this.xShape = xs[0].getShape();
        Tensor y = Utils.sum(xs[0], axis, keepDims);
        return new Tensor[]{y};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Tensor gy = Utils.reshapeSumBackward(gys[0].getData(), xShape, keepDims, axis);
        //Variable gx = new Variable(Utils.broadcastTo(gy, xShape));
        Variable gx = new Variable(gy);
        return new Variable[]{gx};
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x;
        Tensor tensor;
        int n;
        System.out.println("1D");
        double[] vector = new double[5];
        tensor = new Tensor(vector);
        n = 0;
        for (int i = 0; i < tensor.getLength(); i++) {
            tensor.getValues()[n] = n++;
        }
        x = new Variable(tensor);
        System.out.println(x);
        System.out.println(x.sum());

        double[][] matrix = new double[3][5];
        System.out.println("2D");
        tensor = new Tensor(matrix);
        n = 0;
        for (int i = 0; i < tensor.getLength(); i++) {
            tensor.getValues()[n] = n++;
        }
        x = new Variable(tensor);
        System.out.println(x);
        System.out.println(x.sum());
        System.out.println(x.sum(0));
        System.out.println(x.sum(1));
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        /*
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();
        f = new Sum(0);
        y = f.forward(xs)[0];
        System.out.println("axis 0:");
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);
        System.out.println("axis 1:");
        f = new Sum(1);
        y = f.forward(xs)[0];
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);

         */
    }

}
