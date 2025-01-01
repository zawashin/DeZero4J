package step.step47;

import dezero4j.Function;
import dezero4j.Variable;
import tensor4j.Tensor;
import tensor4j.Utils;

import java.io.Serial;
import java.util.Arrays;

public class SoftmaxCrossEntropy extends Function {

    public static void main(String[] args) {
        Variable x = new Variable(new double[][]{{-0.61505778, -0.42790161, 0.31733289},
                {-0.76395313, -0.2497645, 0.18591382},
                {-0.52006391, -0.96254612, 0.57818938},
                {-0.94252164, -0.50307479, 0.17576323}});
        //Variable t = new Variable(new double[][]{{2}, {0}, {1}, {0}});
        //Variable t = new Variable(new double[][]{{2}, {0}, {1}, {0}});
        Variable t = new Variable(new double[]{2, 0, 1, 0});

        Function f = new SoftmaxCrossEntropy();
        Variable loss = f.forward(x, t)[0];
        System.out.println(loss);
        loss.backward();
        System.out.println(x.getGrad());
    }

    @Serial
    private static final long serialVersionUID = -7757218444635889221L;
    double[] prob;
    double eps;
    int[] t;
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
        t = new int[xs[1].getLength()];
        for (int i = 0; i < t.length; i++) {
            t[i] = (int) xs[1].getValues()[i];
        }
        double[] maxValues = new double[shape0];
        for (int i = 0; i < shape0; i++) {
            maxValues[i] = xs[0].getValues()[i * shape1];
            for (int j = 1; j < shape1; j++) {
                maxValues[i] = Math.max(maxValues[i], xs[0].getValues()[i * shape1 + j]);
            }
        }

        // Compute softmax
        prob = new double[shape0 * shape1];
        for (int i = 0; i < shape0; i++) {
            double sumExp = 0.0;
            for (int j = 0; j < shape1; j++) {
                prob[i * shape1 + j] = Math.exp(xs[0].getValues()[i * shape1 + j] - maxValues[i]);
                sumExp += prob[i * shape1 + j];
            }
            for (int j = 0; j < shape1; j++) {
                prob[i * shape1 + j] /= sumExp;
            }
        }

        // Compute cross entropy loss
        double loss = 0.0;
        for (int i = 0; i < shape0; i++) {
            loss -= Math.log(prob[i * shape1 + t[i]] + eps);
        }
        loss /= shape0;

        return new Tensor[]{new Tensor(loss)};
    }

    @Override
    public Variable[] backward(Variable... gys) {

        // Compute softmax and gradients
        double[][] gx = new double[shape0][shape1];
        double scale = gys[0].getValues()[0] / shape0;
        for (int i = 0; i < shape0; i++) {
            for (int j = 0; j < shape1; j++) {
                if (j == t[i]) {
                    gx[i][j] = prob[i * shape1 + j] - 1;
                } else {
                    gx[i][j] = prob[i * shape1 + j];
                }
                // Scale gradient by batch size
                gx[i][j] *= scale;
            }
        }

        return new Variable[]{new Variable(gx).reshape(inputs[0].getShape()), null};
    }

}

