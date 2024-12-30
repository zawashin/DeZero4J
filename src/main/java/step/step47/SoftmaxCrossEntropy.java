package step.step47;

import dezero4j.Function;
import dezero4j.Variable;
import tensor4j.Tensor;

import java.io.Serial;
import java.util.Arrays;

public class SoftmaxCrossEntropy extends Function {

    public static void main(String[] args) {
        {
            // Example inputs
            Tensor logits = new Tensor(new double[][]{{2.0, 1.0, 0.1}});
            Tensor labels = new Tensor(new double[][]{{1.0, 0.0, 0.0}});

            Function f = new SoftmaxCrossEntropy();
            // Forward pass
            Variable x = new Variable(logits);
            Variable t = new Variable(labels);
            Variable y = f.forward(x, t)[0];
        /*
        Cross-Entropy Loss: 0.4170300162778335
         */
            System.out.println("Cross-Entropy Loss: " + y);

            // Backward pass
            y.backward(true, true);
            Variable gx = x.getGrad();
            System.out.println("Gradients:");
            System.out.println(gx);
        }
        {
            // Example inputs
            Tensor logits = new Tensor(new double[][]{{2.0, 1.0, 0.1}, {1.0, 2.0, 3.0}});
            Tensor labels = new Tensor(new double[][]{{1.0, 0.0, 0.0}, {0.0, 0.0, 1.0}});

            Function f = new SoftmaxCrossEntropy();
            // Forward pass
            Variable x = new Variable(logits);
            Variable t = new Variable(labels);
            Variable y = f.forward(x, t)[0];
        /*
        Cross-Entropy Loss: 0.4170300162778335
         */
            System.out.println("Cross-Entropy Loss: " + y);

            // Backward pass
            y.backward(true, true);
            Variable gx = x.getGrad();
            System.out.println("Gradients:");
            System.out.println(gx);
        }
    }

    @Serial
    private static final long serialVersionUID = -7757218444635889221L;
    double[] softmax;
    double eps;
    int[][] indices;
    int shape0, shape1;

    public SoftmaxCrossEntropy() {
        this(1.0e-7);
    }

    public SoftmaxCrossEntropy(double eps) {
        this.eps = eps;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        switch (xs[0].getRank()) {
            case 0:
                shape0 = 1;
                shape1 = 1;
                break;
            case 1:
                shape0 = 1;
                shape1 = xs[0].getShape(0);
                break;
            case 2:
                shape0 = xs[0].getShape(0);
                shape1 = xs[0].getShape(1);
                break;
            default:
                throw new IllegalArgumentException("Rank " + xs[0].getRank() + " is not supported.");
        }

        indices = new int[shape0][shape1];
        for (int i = 0; i < shape0; i++) {
            for (int j = 0; j < shape1; j++) {
                indices[i][j] = i * shape1 + j;
            }
        }
        // Compute softmax
        softmax = new double[shape0 * shape1];
        // 各行ごとに最大値を計算
        double[] maxValues = new double[shape1];
        Arrays.fill(maxValues, Double.NEGATIVE_INFINITY);
        for (int j = 0; j < shape1; j++) {
            double max = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < shape0; i++) {
                max = Math.max(max, xs[0].getValues()[indices[i][j]]);
            }
            maxValues[j] = max;
        }

        for (int i = 0; i < shape0; i++) {
            double sumExp = 0.0;
            for (int j = 0; j < shape1; j++) {
                softmax[i * shape1 + j] = Math.exp(xs[0].getValues()[indices[i][j]] - maxValues[i]);
                sumExp += softmax[indices[i][j]];
            }
            for (int j = 0; j < shape1; j++) {
                softmax[i * shape1 + j] /= sumExp;
            }
        }

        // Compute cross entropy loss
        double loss = 0.0;
        for (int i = 0; i < xs[0].getLength(); i++) {
            loss -= xs[1].getValues()[i] * Math.log(softmax[i] + eps);
        }
        loss /= shape0;

        return new Tensor[]{new Tensor(loss)};
    }

    @Override
    public Variable[] backward(Variable... gys) {

        double[] gx = new double[inputs[0].getLength()];

        // Compute softmax and gradients
        for (int i = 0; i < gx.length; i++) {
            gx[i] = softmax[i] - inputs[1].getValues()[i];
        }

        // Scale gradient by batch size
        double scale = gys[0].getValues()[0] / shape0;
        for (int i = 0; i < gx.length; i++) {
            gx[i] *= scale;
        }

        return new Variable[]{new Variable(gx).reshape(inputs[0].getShape()), null};
    }

}

