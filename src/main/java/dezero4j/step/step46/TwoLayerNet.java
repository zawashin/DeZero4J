package dezero4j.step.step46;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class TwoLayerNet extends Model {

    public TwoLayerNet(int numHidden, int numOutput) {
        super(new int[]{numHidden, numOutput});
    }

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        int n = 100;
        double[][] xArray = new double[n][1];
        double[][] yArray = new double[n][1];
        for (int i = 0; i < xArray.length; i++) {

            /*
            xArray[i] = random.nextDouble();
            yArray[i] = Math.sin(2.0 * Math.PI * xArray[i]) + random.nextDouble();
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
        TwoLayerNet model = new TwoLayerNet(numHiddens, numOutputs);

        double learningRate = 0.2;
        int iters = 10000;
        //iters = 2;
        for (int i = 0; i < iters; i++) {
            Variable y = model.predict(xs);
            Variable loss = y.mse(y0);
            if (i % 1000 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
            }
            model.clearGrads();
            loss.backward(false, true);

            model.update(learningRate);
        }
        Variable y = model.predict(xs);
        for (int i = 0; i < x.getLength(); i++) {
            System.out.println(x.getData().getValues()[i] + "\t" + y.getData().getValues()[i] + "\t" + y0.getData().getValues()[i]);
        }
        for (int i = 0; i < xArray.length; i++) {
            x = new Variable(xArray[i]);
            y = model.predict(new Variable[]{x});
            System.out.println(x.getValues()[0] + "\t" + y.getValues()[0] + "\t" + y0.getValues()[i]);
        }
        /*
         */

    }

}
