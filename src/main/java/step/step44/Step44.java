package step.step44;


import step.Step;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step44 extends Step {

    public static void main(String[] args) {
        new Step44().calc();
    }

    @Override
    public void calc() {
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
        Variable[] xs = new Variable[1];
        Variable x = new Variable(xArray);
        xs[0] = x;
        Variable y0 = new Variable(yArray);

        int numHiddens = 10;
        int numOutputs = 1;

        Affine layer1 = new Affine(numHiddens);
        Affine layer2 = new Affine(numOutputs);

        double learningRate = 0.2;
        int iters = 10000;
        //iters = 2;
        Function f2 = new Sigmoid();
        for (int i = 0; i < iters; i++) {
            Variable[] ys1 = layer1.forward(xs);
            Variable[] ys2 = f2.forward(ys1);
            Variable[] ys3 = layer2.forward(ys2);
            Variable y = ys3[0];
            Variable loss = y.mse(y0);
            if (i % 1000 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
            }
            layer1.clearGrads();
            layer2.clearGrads();
            loss.backward(false, true);

            layer1.update(learningRate);
            layer2.update(learningRate);
        }
        Variable[] ys1 = layer1.forward(xs);
        System.out.println(ys1.length);
        Variable[] ys2 = f2.forward(ys1);
        Variable[] ys3 = layer2.forward(ys2);
        Variable y = ys3[0];
        for (int i = 0; i < x.getLength(); i++) {
            System.out.println(x.getValues()[i] + "\t" + y.getValues()[i] + "\t" + y0.getValues()[i]);
        }

    }
}
