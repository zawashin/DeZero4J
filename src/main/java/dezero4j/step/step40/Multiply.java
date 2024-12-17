package dezero4j.step.step40;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Multiply extends Function {
    boolean broadcast = false;

    @Override
    public Tensor[] forward(Tensor... xs) {
        broadcast = !Arrays.equals(xs[0].getShape(), xs[1].getShape());
        if (broadcast) {
            int[] bsShape = Utils.broadcastShape(xs[0], xs[1]);
            Tensor[] xs_ = new Tensor[]{xs[0].broadcastTo(bsShape), xs[1].broadcastTo(bsShape)};
            return new Tensor[]{xs_[0].multiply(xs_[1])};
        } else {
            return new Tensor[]{xs[0].multiply(xs[1])};
        }
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[2];
        if (broadcast) {
            gxs[0] = (gys[0].multiply(inputs[1])).sumTo(inputs[0].getShape());
            gxs[1] = (gys[0].multiply(inputs[0])).sumTo(inputs[1].getShape());
        } else {
            gxs[0] = gys[0].multiply(inputs[1]);
            gxs[1] = gys[0].multiply(inputs[0]);
        }
        return gxs;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[1] = new Variable(3);
        System.out.println("x");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        Function add = new Multiply();
        Variable y = add.forward(xs)[0];
        System.out.println(y);
        y.backward(false, true);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
    }
}
