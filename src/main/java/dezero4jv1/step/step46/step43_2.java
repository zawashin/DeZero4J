package dezero4jv1.step.step46;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class step43_2 {

    public static Variable predict(Variable x, Variable[] w, Variable[] b) {
        return ((x.linear(w[0], b[0])).sigmoid()).linear(w[1], b[1]);
    }

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        int n = 100;
        double[][] xArray = new double[n][1];
        double[][] yArray = new double[n][1];
        for (int i = 0; i < xArray.length; i++) {
            xArray[i][0] = (double) i / n;
            yArray[i][0] = Math.sin(2.0 * Math.PI * xArray[i][0]) + random.nextDouble();
        }
        Variable x = new Variable(xArray);
        Variable y0 = new Variable(yArray);

        int numInputs = 1;
        int numHiddens = 10;
        int numOutputs = 1;

        Variable[] w = new Variable[2];
        Variable[] b = new Variable[2];
        w[0] = new Variable(TensorUtils.createRandomTensor(0.01, numInputs, numHiddens));
        b[0] = new Variable(new double[numInputs][numHiddens]);
        w[1] = new Variable(TensorUtils.createRandomTensor(0.01, numHiddens, numOutputs));
        b[1] = new Variable(new double[numOutputs][numOutputs]);

        double learningRate = 0.2;
        int iters = 10000;

        for (int i = 0; i < iters; i++) {
            //Variable y = ((x.linear(w1, b1)).sigmoid()).linear(w2, b2);
            Variable y = predict(x, w, b);
            Variable loss = y.mse(y0);
            if (i % 100 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
                System.gc();
            }
            System.out.println("Loss[" + i + "] = " + loss);
            w[0].clearGrad();
            b[0].clearGrad();
            w[1].clearGrad();
            b[1].clearGrad();
            loss.backward(false, true);
            Variable dw1 = w[0].grad;
            Variable db1 = b[0].grad;
            Variable dw2 = w[1].grad;
            Variable db2 = b[1].grad;

            w[0].minusAssign(dw1.times(learningRate));
            b[0].minusAssign(db1.times(learningRate));
            w[1].minusAssign(dw2.times(learningRate));
            b[1].minusAssign(db2.times(learningRate));
        }
        Variable y = predict(x, w, b);
        for(int i = 0; i < x.getLength(); i++) {
            System.out.println(x.getData().getValues()[i] + "\t" + y.getData().getValues()[i] + "\t" + y0.getData().getValues()[i]);
        }
    }

}
