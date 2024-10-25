package dezero4jv1.step.step43;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class BroadcastTo extends Function {

    private int[] shape;

    {
        shape = new int[TensorUtils.RANK_MAX];
        Arrays.fill(shape, 1);
    }

    public BroadcastTo() {
        numInputs = 1;
        numOutputs = 1;
    }

    public BroadcastTo(int[] shape) {
        this();
        setShape(shape);
    }

    public void setShape(int[] shape) {
        if (shape.length == TensorUtils.RANK_MAX) {
            this.shape = shape.clone();
        } else {
            System.arraycopy(shape, 0, this.shape, 0, shape.length);
        }
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = xs[0].broadcastTo(shape);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0].sumTo(inputs[0].getShape());
        return gxs;
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
}
