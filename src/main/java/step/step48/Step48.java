package step.step48;

import dezero4j.*;
import step.Step;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step48 extends Step {

    public static void main(String[] args) {
        Step step = new Step48();
        step.calc();
    }

    @Override
    public void calc() {
        SpiralDataset spiral = new SpiralDataset();
        spiral.generate(true);
        int max_epoch = 10000;
        int batch_size = 1;
        double[][] xy = spiral.getX();
        int[] labels = spiral.getTarget();
        Variable x = new Variable(xy);
        // int[]をdouble[]に変換
        Variable t = new Variable(Arrays.stream(spiral.getTarget()).asDoubleStream().toArray());
        Model model = new TwoLayerNet(10, 3);

        Optimizer optimizer = new SGD(model, 0.2);
        int data_size = x.getShape()[0];
        int iters = data_size / batch_size;
        iters = 50000;

        Function f = new SoftmaxCrossEntropy();

        for (int i = 0; i < iters; i++) {
            Variable y = model.predict(x);
            Variable loss = f.forward(y, t)[0];
            if (i % 1000 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
            }
            if (loss.getValues()[0] < 0.01) {
                break;
            }

            model.clearGrads();
            loss.backward(false, true);

            optimizer.update();
            // ↓　忘れるとOutOfMemoryで落ちる
            x.clearGrad();
        }
        Variable te = VariableUtils.softMaxArgMax(model.predict(x));
        for (int i = 0; i < x.getShape()[0]; i++) {
            System.out.println(t.getValues()[i] + " " + te.getValues()[i]);
        }
        int n = 25;
        double[] x0 = new double[n];
        double[] y0 = new double[n];
        Variable xi = new Variable(new double[][]{{0.5, 0.5}, {-0.5, -0.5}});
        Variable yi = VariableUtils.softMaxArgMax(model.predict(xi));
        System.out.println(xi + " " + yi);
    }
}
