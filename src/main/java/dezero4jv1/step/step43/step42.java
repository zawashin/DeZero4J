package dezero4jv1.step.step43;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class step42 {

    public static Variable predict(Variable x, Variable w, Variable b) {
        Function linear = new Linear();
        return linear.forward(x, w, b)[0];
    }

    public static void main(String[] args) {
        Random random = new Random(0);
        int n = 101;
        double[][] xArray = new double[n][1];
        double[][] yArray = new double[n][1];
        for (int i = 0; i < xArray.length; i++) {
            xArray[i][0] = (double) i/(n - 1);
            yArray[i][0] = 5.0 + 2.0 * xArray[i][0] + random.nextDouble();
        }
        Variable x = new Variable(xArray);
        Variable y0 = new Variable(yArray);

        Variable w = new Variable(0);
        Variable b = new Variable(0);
        double learningRate = 0.1;
        int iters = 100;

        //System.out.println(w + "\t" + b);
        for (int i = 0 ; i < iters; i++) {
            //Variable y = (x.dot(w)).plus(b);
            Variable y = predict(x, w, b);
            Function mes = new MeanSquaredError();
            Variable loss = mes.forward(y, y0)[0];
            System.out.println("Loss = " + loss);
            w.clearGrad();
            b.clearGrad();
            loss.backward(false, true);
            Variable dw = w.grad;
            Variable db = b.grad;
            w.minusAssign(dw.times(learningRate));
            b.minusAssign(db.times(learningRate));
        }
        System.out.println(w + "\t" + b);
    }
}
