package dezero4j.step.step45;

import dezero4j.step.Step;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step42 extends Step {

    public static void main(String[] args) {
        new Step42().calc();
    }

    @Override
    public void calc() {
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

        Variable w = new Variable(new double[][]{{0}});
        //Variable w = new Variable(new double[][]{{0}}); // <- 動く
        Variable b = new Variable(0);
        double learningRate = 0.1;

        int iters = 100;
        Function mes = new MeanSquaredError();

        for (int i = 0; i < iters; i++) {
            Variable y = (x.matmul(w)).add(b);
            //System.out.println("Predict = " + y.transpose());
            Variable loss = mes.forward(y, y0)[0];
            System.out.println("Loss = " + loss);
            w.clearGrad();
            b.clearGrad();
            loss.backward(false, true);
            Variable dw = w.grad;
            Variable db = b.grad;
            w.subtractAssign(dw.multiply(learningRate));
            b.subtractAssign(db.multiply(learningRate));
            //System.out.print(w.grad + "\t" + b.grad + "\t");
        }
        System.out.println(w + "\t" + b);
        for (int i = 0; i < xArray.length; i++) {
            x = new Variable(xArray[i][0]);
            Variable y = (x.matmul(w)).add(b);
            System.out.print(x.getValues()[0] + "\t");
            System.out.print(y.getValues()[0] + "\t");
            System.out.println(yArray[i][0]);
        }
    }
}
