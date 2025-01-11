package dezero4j;

import tensor4j.Tensor;
import tensor4j.TensorUtils;

import java.io.Serial;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class SumTo extends Function {

    @Serial
    private static final long serialVersionUID = -582887011392887853L;
    private final int[] shape;

    public SumTo(int[] shape) {
        this.shape = shape;
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
        y = x.sumTo(1, 1);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();


        System.out.println("{4, 5, 6}");
        xs[0] = new Variable(new double[]{4, 5, 6});
        x = xs[0];
        System.out.println(Arrays.toString(x.getShape()));
        System.out.println(x);
        y = x.sumTo(1);
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);


        System.out.println("{4, 5, 6}T");
        xs[0] = new Variable(new double[][]{{4}, {5}, {6}});
        x = xs[0];
        System.out.println(Arrays.toString(x.getShape()));
        System.out.println(x);
        y = x.sumTo(1, 1);
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

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{TensorUtils.sumTo(xs[0], shape)};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return new Variable[]{gys[0].broadcastTo(inputs[0].getShape())};
    }
}
