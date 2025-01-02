package dezero4j;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.io.Serial;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Linear extends Function {

    @Serial
    private static final long serialVersionUID = 3837388304530009698L;

    // 順伝播
    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[1];
        Tensor x = xs[0];
        Tensor w = xs[1];
        Tensor y = x.dot(w);
        if (xs.length >= 3 && xs[2] != null) {
            Tensor b = xs[2];
            int[] shape = Utils.broadcastShape(y, xs[2]);
            Tensor y_ = y.broadcastTo(shape);
            Tensor b_ = b.broadcastTo(shape);
            ys[0] = y_.add(b_);
        } else {
            ys[0] = y;
        }
        return ys;
    }

    // 逆伝播
    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[inputs.length];
        Variable x = inputs[0];
        Variable w = inputs[1];
        Variable gy = gys[0];
        gxs[0] = gy.matmul(w.transpose());
        gxs[1] = (x.transpose()).matmul(gy);
        if (inputs.length == 3 && inputs[2] != null) {
            gxs[2] = gy.sumTo(inputs[2].getShape());
        }
        if (!Arrays.equals(gxs[0].getShape(), inputs[0].getShape())) {
            gxs[0] = gxs[0].reshape(inputs[0].getShape());
        }
        if (!Arrays.equals(gxs[1].getShape(), inputs[1].getShape())) {
            gxs[1] = gxs[1].reshape(inputs[1].getShape());
        }
        return gxs;
    }
}

