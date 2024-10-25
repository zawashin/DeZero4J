package dezero4jv1;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class SGD extends Optimizer {

    private double learningRate = 0.01;

    public SGD(Model model, double learningRate) {
        super(model);
        this.learningRate = learningRate;
    }

    @Override
    public void update() {
        target.update(learningRate);
        for (Parameter param: target.params) {
            param.minusAssign((param.grad).times(learningRate));
        }
    }

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        int n = 100;
        double[] xArray = new double[n];
        double[] yArray = new double[n];
        for (int i = 0; i < xArray.length; i++) {
            xArray[i] = random.nextDouble();
            yArray[i] = Math.sin(2.0 * Math.PI * xArray[i]) + random.nextDouble();
        }
        Variable[] xs = new Variable[1];
        Variable x = new Variable(TensorUtils.createTransposedTensor(xArray));
        xs[0] = x;
        Variable y0 = new Variable(TensorUtils.createTransposedTensor(yArray));

        int numHiddens = 10;
        int numOutputs = 1;
        Model model = new Model();
        model.addLayer(numHiddens);
        model.addLayer(numOutputs);

        Optimizer optimizer = new SGD(model, 0.2);
        int iters = 10000;
        for (int i = 0; i < iters; i++) {
            Variable y = model.predict(xs);
            Variable loss = y.mse(y0);
            if (i % 100 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
                System.gc();
            }
            model.clearGrads();
            loss.backward(false, true);

            optimizer.update();
        }
        Variable y = model.predict(xs);
        for (int i = 0; i < x.getLength(); i++) {
            System.out.println(x.getData().getValues()[i] + "\t" + y.getData().getValues()[i] + "\t" + y0.getData().getValues()[i]);
        }

    }
}
