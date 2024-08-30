package dezero4j.step.step34.step39;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sum extends Function {

    private int axis = TensorOperator.RANK_MAX;
    private int[] xShape;

    public Sum() {
        numInputs = 1;
        numOutputs = 1;
    }

    public Sum(int axis) {
        this();
        this.axis = axis;
    }

    public void setAxis(int axis) {
        this.axis = axis;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        xShape = xs[0].getShape();
        System.out.println(Arrays.toString(xShape));
        ys[0] = xs[0].sum(axis);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        /*
        gxs[0] = VariableUtils.reshapeSumBackward(gys[0], xShape, axis);

         */
        gxs[0] = gys[0].broadcastTo(xShape);
        return gxs;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable[] ys;
        Variable x;
        Variable y;
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[0] = x;
        System.out.println("x");
        System.out.println(x);
        Function f = new Sum();
        ys = f.forward(xs);
        y = ys[0];
        System.out.println("y");
        System.out.println("axis *:");
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();
        /*
        */
        f = new Sum(0);
        ys = f.forward(xs);
        y = ys[0];
        System.out.println("axis 0:");
        System.out.println(Arrays.toString(y.getShape()));
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();
        f = new Sum(1);
        y = f.forward(xs)[0];
        System.out.println("axis 1:");
        System.out.println(Arrays.toString(y.getShape()));
        System.out.println(y);

        /*
        ys = f.forward(xs);
        System.out.println(ys[0]);
        f = new Sum(1);
        ys = f.forward(xs);
        System.out.println(ys[0]);
         */

    }
}
