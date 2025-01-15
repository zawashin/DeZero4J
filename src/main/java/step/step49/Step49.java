package step.step49;

import dezero4j.*;
import numviz.NvFrame;
import step.Step;
import step.step48.SpiralDataset;
import step.step48.XYZPainter;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step49 extends Step {

    public static void main(String[] args) {
        Step step = new Step49();
        step.calc();
    }

    @Override
    public void calc() {
        SpiralDataset spiral = new SpiralDataset();
        spiral.generate(true);
        int max_epoch = 10000;
        double[][] xy = spiral.getX();
        // int[]をdouble[]に変換
        double[] target = Arrays.stream(spiral.getTarget()).asDoubleStream().toArray();
        Variable x = new Variable(xy);
        Variable t = new Variable(target);
        int batchSize = 5;
        //Variable[][] miniBatch = MiniBatch.create(xy, target, batchSize);
        int data_size = x.getShape()[0];
        int iters = data_size / batchSize;
        Model model = new TwoLayerNet(10, 3);
        Optimizer optimizer = new SGD(model, 0.2);
        iters = 50000;

        Function f = new SoftmaxCrossEntropy();

        for (int i = 0; i < iters; i++) {
            Variable y = model.predict(x);
            Variable loss = f.forward(y, t)[0];
            if (i % 1000 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
            }
            if (loss.getValues()[0] < 0.05) {
                break;
            }

            loss.backward(false, true);

            optimizer.update();
            model.clearGrads();
            // ↓　忘れるとOutOfMemoryで落ちる
            x.clearGrad();
        }
        /*
        Variable te = VariableUtils.softMaxArgMax(model.predict(x));
        for (int i = 0; i < x.getShape()[0]; i++) {
            System.out.println(t.getValues()[i] + " " + te.getValues()[i]);
        }

         */
        int n = 100;
        double[] x0 = new double[n];
        double[] y0 = new double[n];
        double d = 2.0 / n;
        for (int i = 0; i < n; i++) {
            x0[i] = y0[i] = -1.0 + d * 0.5 + i * d;
        }
        xy = new double[n * n][2];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                xy[cnt][0] = x0[i];
                xy[cnt][1] = y0[j];
                cnt++;
            }
        }
        Variable xi = new Variable(xy);
        Variable yi = VariableUtils.softMaxArgMax(model.predict(xi));
        System.out.println(xi + " " + yi);
        double[][] xyt = new double[n * n][3];
        for (int i = 0; i < n * n; i++) {
            xyt[i][0] = xy[i][0];
            xyt[i][1] = xy[i][1];
            xyt[i][2] = (double) yi.getValues()[i];
        }

        XYZPainter painter = new XYZPainter(800, 800, xyt);
        final NvFrame frame = new NvFrame(painter);
        frame.setTitle("Spiral Data");
        //painter.saveImageAsPng("Spiral");
        frame.setVisible(true);
    }
}
