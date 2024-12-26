package step.step44;

import tensor4j.Tensor;
import tensor4j.Utils;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class BroadcastTo extends Function {

    private final int[] shape;

    public BroadcastTo(int[] shape) {
        this.shape = shape;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        xs[0] = new Variable(new double[]{1, 2, 3});
        Variable x = xs[0];
        System.out.println("{1, 2, 3}");
        System.out.println(x);
        BroadcastTo f = new BroadcastTo(new int[]{2, 3});
        Variable y = f.forward(xs)[0];
        System.out.println("to 2 x 3:↓");
        System.out.println(y);
        y.backward(false, true);
        System.out.println("∂y/∂x");
        System.out.println(x.grad);
        System.out.println();

        System.out.println("{{1, 2, 3},");
        System.out.println(" {4, 5, 6}}");
        xs = new Variable[1];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        x = xs[0];
        System.out.println(x);
        System.out.println("to 2 x 6:→");
        f = new BroadcastTo(new int[]{2, 6});
        y = f.forward(xs)[0];
        System.out.println(y);
        x.clearGrad();
        System.out.println("∂y/∂x");
        y.backward(false, true);
        System.out.println(x.grad);
        System.out.println();

        f = new BroadcastTo(new int[]{4, 3});
        y = f.forward(xs)[0];
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);
        f = new BroadcastTo(new int[]{4, 6});
        y = f.forward(xs)[0];
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{Utils.broadcastTo(xs[0], shape)};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return new Variable[]{gys[0].sumTo(inputs[0].getShape())};
    }
}
