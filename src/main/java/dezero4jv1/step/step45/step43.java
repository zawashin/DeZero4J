package dezero4jv1.step.step45;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class step43 {

    public static Variable predict(Variable x, Variable w1, Variable b1, Variable w2, Variable b2) {
        return ((x.linear(w1, b1)).sigmoid()).linear(w2, b2);
    }

    public static Variable predict(Variable x, Variable[] w, Variable[] b) {
        Function f1 = new Linear();
        Variable[] ys1 = f1.forward(x, w[0], b[0]);
        Function f2 = new Sigmoid();
        Variable[] ys2 = f2.forward(ys1);
        Function f3 = new Linear();
        Variable[] ys3 = f3.forward(ys2[0], w[1], b[1]);
        return ys3[0];
        //return ((x.linear(w[0], b[0])).sigmoid()).linear(w[1], b[1]);
        //return ((x.linear(w[0], b[0])).sigmoid()).linear(w[1], b[1]);
    }

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        int n = 100;
        double[][] xArray = new double[n][1];
        double[][] yArray = new double[n][1];
        for (int i = 0; i < xArray.length; i++) {
            /*
            xArray[i][0] = random.nextDouble();
            yArray[i][0] = Math.sin(2.0 * Math.PI * xArray[i][0]) + random.nextDouble();

             */
            xArray[i][0] = (double) i / n;
            yArray[i][0] = Math.sin(2.0 * Math.PI * xArray[i][0]);
        }
        Variable x = new Variable(xArray);
        Variable y0 = new Variable(yArray);

        int numInputs = 1;
        int numHiddens = 10;
        int numOutputs = 1;

        Variable w0 = new Variable(TensorUtils.createRandomTensor(numInputs, numHiddens));
        //Variable w0 = new Variable(TensorUtils.createRandomTensor(0.0100 + 1e-5, numInputs, numHiddens));
        /*
        // デバッグ用
        int k = 0;
        for(int i = 0; i < numInputs; i++) {
            for(int j = 0; j < numHiddens; j++) {
                w0.getValues()[k++] = 0.01 * j;
            }
        }

         */
        Variable b0 = new Variable(new double[numHiddens]);

        Variable w1 = new Variable(TensorUtils.createRandomTensor(numHiddens, numOutputs));
        //Variable w1 = new Variable(TensorUtils.createRandomTensor(0.01, numHiddens, numOutputs));
        /*
        // デバッグ用
        k = 0;
        for(int i = 0; i < numHiddens; i++) {
            for(int j = 0; j < numOutputs; j++) {
                w1.getValues()[k++] = 0.01 * (10 - i);
            }
        }
         */
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
            if (i % 100 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
                System.gc();
            }
            //System.out.println("Loss[" + i + "] = " + loss);
            w0.clearGrad();
            b0.clearGrad();
            w1.clearGrad();
            b1.clearGrad();
            loss.backward(false, true);
            Variable dw0 = w0.grad;
            Variable db0 = b0.grad;
            Variable dw1 = w1.grad;
            Variable db1 = b1.grad;
            /*
            System.out.println("w0 = " + w0);
            System.out.println("b0 = " + b0);
            System.out.println("w1 = " + w1.transpose());
            System.out.println("b1 = " + b1);
            System.out.println("dw0 = " + dw0);
            System.out.println("db0 = " + db0);
            System.out.println("dw1 = " + dw1.transpose());
            System.out.println("db1 = " + db1);

             */

            w0.minusAssign(dw0.times(learningRate));
            b0.minusAssign(db0.times(learningRate));
            w1.minusAssign(dw1.times(learningRate));
            b1.minusAssign(db1.times(learningRate));
        }
        /*
         */
        Variable y = predict(x, w0, b0, w1, b1);
        for(int i = 0; i < x.getLength(); i++) {
            System.out.println(x.getData().getValues()[i] + "\t" + y.getData().getValues()[i] + "\t" + y0.getData().getValues()[i]);
        }

    }

}
