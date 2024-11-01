package dezero4j.step.step37;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sum extends Function {

    private int axis;
    private int[] shape;

    public Sum(int axis) {
        this.axis = axis;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        this.shape = xs[0].getShape().clone();
        return new Tensor[]{xs[0].sum(axis)};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return new Variable[]{gys[0].broadcastTo(shape)};

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
