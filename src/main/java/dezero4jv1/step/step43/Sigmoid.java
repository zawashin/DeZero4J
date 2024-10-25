package dezero4jv1.step.step43;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sigmoid extends Function {

    public Sigmoid() {
        numInputs = 1;
        numOutputs = 1;
    }

    // 順伝播
    @Override
    public Tensor[] forward(Tensor[] xs) {
        Tensor[] ys = new Tensor[numOutputs];
        Tensor x = xs[0];
        int length = x.getLength();
        double[] values = new double[length];
        Arrays.fill(values,0.5);
        Tensor param05 = new Tensor(values, x.getShape());
        Tensor y = (((x.times(param05)).tanh()).times(param05)).plus(param05);
        ys[0] = y;
        return ys;
    }

    // 逆伝播
    @Override
    public Variable[] backward(Variable[] gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable y = outputs[0];
        Variable gy = gys[0];
        Variable param1 = new Variable(1.0, y.getShape());
        gxs[0] = gy.times(y).times(param1.minus(y));
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
