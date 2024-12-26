package dezero4j;

import tensor4j.Tensor;
import tensor4j.Utils;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sigmoid extends ActivationFunction {

    // 順伝播
    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor param05 = Utils.fill(0.5, xs[0].getShape());
        return new Tensor[]{(((xs[0].multiply(param05)).tanh()).multiply(param05)).add(param05)};
    }

    // 逆伝播
    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[1];
        Variable y = outputs[0];
        Variable gy = gys[0];
        Variable param1 = new Variable(1.0);
        //Variable param1 = new Variable(1.0, y.getShape());
        gxs[0] = gy.multiply(y).multiply(param1.subtract(y));
        return gxs;
    }

    public static void main(String[] args) {
        Function sigmoid = new Sigmoid();
        Variable x = new Variable(new double[]{-5, -4, -2, 0, 3, 4, 5});
        Variable y = sigmoid.forward(x)[0];
        System.out.println(x);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.getGrad());
    }
}
