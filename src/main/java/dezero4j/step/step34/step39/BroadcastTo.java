package dezero4j.step.step34.step39;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class BroadcastTo extends Function {

    private int[] shape;
    private int[] xShape;

    {
        shape = new int[TensorOperator.RANK_MAX];
        Arrays.fill(shape, 1);
        xShape = new int[TensorOperator.RANK_MAX];
        Arrays.fill(xShape, 1);
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
        if (shape.length == TensorOperator.RANK_MAX) {
            this.shape = shape.clone();
        } else {
            System.arraycopy(shape, 0, this.shape, 0, shape.length);
        }
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numInputs];
        xShape = xs[0].getShape().clone();
        ys[0] = TensorOperator.broadcastTo(xs[0], shape);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0].sumTo(xShape);
        return gxs;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        xs[0] = new Variable(new double[]{1, 2, 3});
        Variable x = xs[0];
        System.out.println(x);
        BroadcastTo f = new BroadcastTo(new int[]{2, 3});
        Variable y = f.forward(xs)[0];
        System.out.println(y);
        f = new BroadcastTo(new int[]{3, 3});
        y = f.forward(xs)[0];
        System.out.println(y);
        //y.backward(false, true);
        //System.out.println(x.grad);

        xs[0] = new Variable(1);
        y = xs[0].broadcastTo(new int[]{4, 3});
        System.out.println(y);
        /*
        xs[0] = new Variable(new double[]{1, 2, 3});
        System.out.println(xs[0]);
        y = xs[0].broadcastTo(new int[]{2,3});
        System.out.println(y);

         */
    }
}
