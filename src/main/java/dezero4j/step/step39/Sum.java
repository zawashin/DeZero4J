package dezero4j.step.step39;

import tensor4j.Tensor;
import tensor4j.Utils;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sum extends Function {

    private final int axis;
    private int[] xShape;
    private final boolean keepDims;

    public Sum() {
        this(-1, true);
    }

    public Sum(int axis) {
        this(axis, true);
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
        Tensor gy = Utils.reshapeSumBackward(gys[0].getData(), xShape, axis, keepDims);
        Variable gx = new Variable(Utils.broadcastTo(gy, xShape));
        return new Variable[]{gx};
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x;
        Variable y;
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[0] = x;
        System.out.println("x");
        System.out.println(x);
        Function f = new Sum();
        y = f.forward(xs)[0];
        System.out.println("y");
        System.out.println("axis *:");
        System.out.println(y);
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
    }

}
