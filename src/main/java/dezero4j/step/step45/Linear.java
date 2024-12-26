package dezero4j.step.step45;

import tensor4j.Tensor;
import tensor4j.Utils;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Linear extends Function {

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

    public static void main(String[] args) {
        Random random = new Random(0);
        int n = 101;
        double[][] xArray = new double[n][1];
        double[][] yArray = new double[n][1];
        for (int i = 0; i < xArray.length; i++) {
            xArray[i][0] = (double) i / (n - 1);
            yArray[i][0] = 5.0 + 2.0 * xArray[i][0] + random.nextDouble();
        }
        Variable x = new Variable(xArray);
        Variable y0 = new Variable(yArray);

        Variable w = new Variable(0);
        Variable b = new Variable(0);

        double learningRate = 0.1;
        int iters = 1000;

        for (int i = 0; i < iters; i++) {
            //Function linear = new Linear();
            //Variable y = linear.forward(x, w, b)[0];
            Variable y = x.linear(w, b);
            //Function mes = new MeanSquaredError();
            //Variable loss = mes.forward(y, y0)[0];
            Variable loss = y.mse(y0);
            System.out.println("Loss = " + loss);
            w.clearGrad();
            b.clearGrad();
            loss.backward(false, true);
            Variable dw = w.grad;
            Variable db = b.grad;
            w.subtractAssign(dw.multiply(learningRate));
            b.subtractAssign(db.multiply(learningRate));
        }
        System.out.println(w + "\t" + b);
    }
}

