package dezero4jv1.step.step45;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Plus extends Function {

    public Plus() {
        numInputs = 2;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        int[] broadcast = TensorUtils.broadcastShape(xs[0], xs[1]);
        Tensor[] xs_ = new Tensor[numInputs];
        xs_[0] = xs[0].broadcastTo(broadcast);
        xs_[1] = xs[1].broadcastTo(broadcast);
        ys[0] = TensorOperator.plus(xs_);
        return ys;
    }

    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        if(!Arrays.equals(inputs[0].getShape(), inputs[1].getShape())) {
            gxs[0] = gys[0].sumTo(inputs[0].getShape());
            gxs[1] = gys[0].sumTo(inputs[1].getShape());
        } else {
            gxs[0] = gys[0].clone();
            gxs[1] = gys[0].clone();
        }
        return gxs;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[1] = new Variable(1);
        System.out.println("x");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        Function plus = new Plus();
        Variable y = plus.forward(xs)[0];
        System.out.println(y);
        y.backward(false, true);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
    }
}
