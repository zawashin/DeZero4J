package step.step47;

import dezero4j.Function;
import dezero4j.Variable;
import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
import java.io.Serial;
import java.util.Arrays;

public class SoftmaxCrossEntropy extends Function {

    @Serial
    private static final long serialVersionUID = -7757218444635889221L;
    private int[][] index;
    double[] maxValues;

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor x = xs[0];
        Tensor t = xs[1];

        // Compute softmax
        double[][] xData = x.getData();
        double[][] softmax = new double[xData.length][xData[0].length];
        maxValues = Arrays.stream(xData).mapToDouble(row -> Arrays.stream(row).max().getAsDouble()).toArray();

        for (int i = 0; i < xData.length; i++) {
            double sumExp = 0.0;
            for (int j = 0; j < xData[i].length; j++) {
                softmax[i][j] = Math.exp(xData[i][j] - maxValues[i]);
                sumExp += softmax[i][j];
            }
            for (int j = 0; j < softmax[i].length; j++) {
                softmax[i][j] /= sumExp;
            }
        }

        // Compute cross entropy loss
        double[][] tData = t.getData();
        double loss = 0.0;
        for (int i = 0; i < xData.length; i++) {
            for (int j = 0; j < xData[i].length; j++) {
                loss -= tData[i][j] * Math.log(softmax[i][j] + 1e-7);
            }
        }
        loss /= xData.length;

        return new Tensor[]{new Tensor(loss)};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable x = inputs[0];
        Variable t = inputs[1];

        double[][] xData = x.getData();
        double[][] tData = t.getData();
        double[][] gradData = gys[0].getData();

        double[][] dx = new double[xData.length][xData[0].length];
        //double[] maxValues = Arrays.stream(xData).mapToDouble(row -> Arrays.stream(row).max().getAsDouble()).toArray();

        // Compute softmax and gradients
        for (int i = 0; i < xData.length; i++) {
            double sumExp = 0.0;
            for (int j = 0; j < xData[i].length; j++) {
                dx[i][j] = Math.exp(xData[i][j] - maxValues[i]);
                sumExp += dx[i][j];
            }
            for (int j = 0; j < dx[i].length; j++) {
                dx[i][j] /= sumExp;
                dx[i][j] -= tData[i][j];
            }
        }

        // Scale gradient by batch size
        double scale = gradData[0][0] / xData.length;
        for (int i = 0; i < dx.length; i++) {
            for (int j = 0; j < dx[i].length; j++) {
                dx[i][j] *= scale;
            }
        }

        return new Variable[]{new Variable(dx)};
    }

}

