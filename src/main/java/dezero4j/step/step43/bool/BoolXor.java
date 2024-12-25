package dezero4j.step.step43.bool;


import dezero4j.step.step43.Variable;
import tensor4j.Utils;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class BoolXor {

    public static Variable predict(Variable x, Variable w1, Variable b1, Variable w2, Variable b2) {
        return ((x.linear(w1, b1)).sigmoid()).linear(w2, b2);
    }

    public static Variable predict(Variable x, Variable[] w, Variable[] b) {
        return ((x.linear(w[0], b[0])).sigmoid()).linear(w[1], b[1]);
    }

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        int numInputs = 2;
        int numHiddens = 10;
        int numOutputs = 1;
        int n = 4;
        double[][] xArray = new double[n][numInputs];
        double[][] yArray = new double[n][numOutputs];
        xArray[0] = new double[]{0, 0};
        xArray[1] = new double[]{1, 0};
        xArray[2] = new double[]{0, 1};
        xArray[3] = new double[]{1, 1};
        yArray[0] = new double[]{0};
        yArray[1] = new double[]{1};
        yArray[2] = new double[]{1};
        yArray[3] = new double[]{0};
        Variable x = new Variable(xArray);
        Variable y0 = new Variable(yArray);


        Variable w0 = new Variable(Utils.random(numInputs, numHiddens));
        Variable b0 = new Variable(new double[numHiddens]);

        Variable w1 = new Variable(Utils.random(numHiddens, numOutputs));
        Variable b1 = new Variable(new double[numOutputs]);
        Variable[] w = new Variable[2];
        w[0] = w0;
        w[1] = w1;
        Variable[] b = new Variable[2];
        b[0] = b0;
        b[1] = b1;

        double learningRate = 0.2;
        int iters = 10000;
        for (int i = 0; i < iters; i++) {
            //Variable y = ((x.linear(w1, b1)).sigmoid()).linear(w2, b2);
            //Variable y = predict(x, w0, b0, w1, b1);
            Variable y = predict(x, w, b);
            Variable loss = y.mse(y0);
            if (i % 1000 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
                System.gc();
            }
            //System.out.println("Loss[" + i + "] = " + loss);
            w0.clearGrad();
            b0.clearGrad();
            w1.clearGrad();
            b1.clearGrad();
            loss.backward(false, true);
            Variable dw0 = w0.getGrad();
            Variable db0 = b0.getGrad();
            Variable dw1 = w1.getGrad();
            Variable db1 = b1.getGrad();

            w0.subtractAssign(dw0.multiply(learningRate));
            b0.subtractAssign(db0.multiply(learningRate));
            w1.subtractAssign(dw1.multiply(learningRate));
            b1.subtractAssign(db1.multiply(learningRate));
        }

        Variable y = predict(x, w0, b0, w1, b1);
        for (int i = 0; i < x.getShape()[0]; i++) {
            System.out.println(x.getValues()[i] + "\t" + y.getValues()[i] + "\t" + y0.getData().getValues()[i]);
        }
        for (int i = 0; i < x.getShape()[0]; i++) {
            Variable xi = new Variable(xArray[i]);
            y = predict(xi, w0, b0, w1, b1);
            System.out.println(xi.getValues()[0] + "\t" + y.getValues()[0] + "\t" + y0.getData().getValues()[i]);
        }

    }

}
