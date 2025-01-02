package step.step53;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */

import dezero4j.*;
import step.Step;

import java.util.Random;

public class Step53 extends Step {
    public static void main(String[] args) {
        Step53 step = new Step53();
        step.calc();
    }

    @Override
    public void calc() {
        Random random = new Random(System.currentTimeMillis());
        int n = 100;
        double[][] xArray = new double[n][1];
        double[][] yArray = new double[n][1];
        for (int i = 0; i < xArray.length; i++) {
            xArray[i][0] = (double) i / n;
            yArray[i][0] = Math.sin(2.0 * Math.PI * xArray[i][0]);
        }
        Variable[] xs = new Variable[1];
        Variable x = new Variable(xArray);
        xs[0] = x;
        Variable y0 = new Variable(yArray);

        int numHiddens = 10;
        int numOutputs = 1;
        Model model = new TwoLayerNet(numHiddens, numOutputs);

        Optimizer optimizer = new SGD(model, 0.2);
        int iters = 10000;
        for (int i = 0; i < iters; i++) {
            Variable y = model.predict(x);
            Variable loss = y.mse(y0);
            if (i % 1000 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
                System.gc();
            }
            model.clearGrads();
            loss.backward(false, true);

            optimizer.update();
            loss.clearGrad();
        }
        Variable y = model.predict(xs);
        for (int i = 0; i < x.getLength(); i++) {
            System.out.println(x.getData().getValues()[i] + "\t" + y.getData().getValues()[i] + "\t" + y0.getData().getValues()[i]);
        }
        try {
            // ファイルに保存
            String filePath = "nn.model";
            ModelSaver.saveModelToFile(model, filePath);
            System.out.println("Model serialized successfully.");

            // ファイルから読み込み
            model = ModelLoader.loadModelFromFile(filePath);

            // 読み込んだ内容を確認
            System.out.println("Model deserialized successfully.");
            //System.out.println("Layers size: " + loadedModel.layers.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        // ファイルから読み込んだ内容を確認するための計算
        System.out.println();
        for (int i = 0; i < x.getLength(); i++) {
            Variable xi = new Variable(xArray[i]);
            y = model.predict(xi);
            System.out.println(xi.getValues()[0] + "\t" + y.getValues()[0] + "\t" + y0.getValues()[i]);
        }
    }
}

