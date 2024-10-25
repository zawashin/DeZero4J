package dezero4jv1.step.step45;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class TwoLayerNet extends Model {


    public TwoLayerNet(int numHidden, int numOutput) {
        super(numHidden, numOutput);
    }

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        int n = 100;
        double[] xArray = new double[n];
        double[] yArray = new double[n];
        for (int i = 0; i < xArray.length; i++) {

            /*
            xArray[i] = random.nextDouble();
            yArray[i] = Math.sin(2.0 * Math.PI * xArray[i]) + random.nextDouble();
             */
            xArray[i] = (double) i / n;
            yArray[i] = Math.sin(2.0 * Math.PI * xArray[i]);
        }
        Variable[] xs = new Variable[1];
        Variable x = new Variable(TensorUtils.createTransposedTensor(xArray));
        xs[0] = x;
        Variable y0 = new Variable(TensorUtils.createTransposedTensor(yArray));

        int numHiddens = 10;
        int numOutputs = 1;
        TwoLayerNet model = new TwoLayerNet(numHiddens, numOutputs);

        double learningRate = 0.2;
        int iters = 10000;
        //iters = 2;
        for (int i = 0; i < iters; i++) {
            Variable y = model.predict(xs);
            Variable loss = y.mse(y0);
            if (i % 100 == 0) {
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
        /*
         */

    }

}
