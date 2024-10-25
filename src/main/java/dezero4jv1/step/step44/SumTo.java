package dezero4jv1.step.step44;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class SumTo extends Function {

    private int[] shape;

    public SumTo() {
        numInputs = 1;
        numOutputs = 1;
    }

    public SumTo(int[] shape) {
        this();
        setShape(shape);
    }

    public void setShape(int[] shape) {
        if (shape.length == TensorUtils.RANK_MAX) {
            this.shape = shape.clone();
        } else {
            this.shape = new int[TensorUtils.RANK_MAX];
            Arrays.fill(this.shape, 1);
            System.arraycopy(shape, 0, this.shape, 0, shape.length);
        }
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorUtils.sumTo(xs[0], shape);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0].broadcastTo(inputs[0].getShape());
        return gxs;
    }

    public static void main(String[] args) {
        Variable[] xs;
        Variable x;
        Variable y;
        xs = new Variable[1];

        System.out.println("6");
        xs[0] = new Variable(6);
        x = xs[0];
        System.out.println(x);
        y = x.sumTo(new int[]{1, 1});
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();


        System.out.println("{4, 5, 6}");
        xs[0] = new Variable(new double[]{4, 5, 6});
        x = xs[0];
        System.out.println(Arrays.toString(x.getShape()));
        System.out.println(x);
        y = x.sumTo(new int[]{1, 1});
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);


        System.out.println("{4, 5, 6}T");
        xs[0] = new Variable(new double[][]{{4}, {5}, {6}});
        x = xs[0];
        System.out.println(Arrays.toString(x.getShape()));
        System.out.println(x);
        y = x.sumTo(new int[]{1, 1});
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        /*
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        x = xs[0];
        System.out.println(x);
        y = x.sumTo(new int[]{2, 1});
        System.out.println("{2, 1}");
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);

         */
    }
}
