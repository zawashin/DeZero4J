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
        spiral.generateSpiral(true);
        int max_epoch = 300;
        int batch_size = 30;
        Variable x = new Variable(spiral.getX());
        // int[]をdouble[]に変換
        Variable t = new Variable(Arrays.stream(spiral.getT()).asDoubleStream().toArray());
        Model model = new TwoLayerNet(10, 3);

        Optimizer optimizer = new SGD(model, 0.2);
        int iters = 10000;
        //iters = 2;
        Function f = new SoftmaxCrossEntropy();
        for (int i = 0; i < iters; i++) {

            Variable y = model.predict(x);
            Variable loss = f.forward(y, t)[0];
            if (i % 1000 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
                System.gc();
            }
            model.clearGrads();
            loss.backward(false, true);

            optimizer.update();
        }
    }
}
