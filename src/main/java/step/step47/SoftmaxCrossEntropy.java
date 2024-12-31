package step.step47;

import dezero4j.Function;
import dezero4j.Variable;
import tensor4j.Tensor;

import java.io.Serial;
import java.util.Arrays;

public class SoftmaxCrossEntropy extends Function {

    public static void main(String[] args) {
        Variable x = new Variable(new double[][]{{-0.61505778, -0.42790161, 0.31733289},
                {-0.76395313, -0.2497645, 0.18591382},
                {-0.52006391, -0.96254612, 0.57818938},
                {-0.94252164, -0.50307479, 0.17576323}});
        Variable t = new Variable(new double[][]{{2, 0, 1, 0}});

        Function f = new SoftmaxCrossEntropy();
        Variable loss = f.forward(x, t)[0];
        System.out.println(loss);
        loss.backward();
        System.out.println(x.getGrad());
        /*
                {
            // Example inputs
            Tensor logits = new Tensor(new double[][]{{2.0, 1.0, 0.1}});
            Tensor labels = new Tensor(new double[][]{{1.0, 0.0, 0.0}});

            Function f = new SoftmaxCrossEntropy();
            // Forward pass
            Variable x = new Variable(logits);
            Variable t = new Variable(labels);
            Variable y = f.forward(x, t)[0];
        //Cross-Entropy Loss: 0.4170300162778335
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
        //Cross-Entropy Loss: 0.4170300162778335
            System.out.println("Cross-Entropy Loss: " + y);

            // Backward pass
            y.backward(true, true);
            Variable gx = x.getGrad();
            System.out.println("Gradients:");
            System.out.println(gx);
        }
         */
    }

    @Serial
    private static final long serialVersionUID = -7757218444635889221L;
    double[] prob;
    double eps;
    boolean broadcast = false;
    Tensor xs1_;
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
        if (!Arrays.equals(xs[0].getShape(), xs[1].getShape())) {
            broadcast = true;
            xs1_ = xs[1].broadcastTo(xs[0].getShape());
        } else {
            broadcast = false;
            xs1_ = xs[1];
        }
        // 各行ごとに最大値を計算
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

        for (int i = 0; i < shape0; i++) {
            for (int j = 0; j < shape1; j++) {
                System.out.print(prob[i * shape1 + j] + " ");
            }
            System.out.println();
        }

        // Compute cross entropy loss
        double loss = 0.0;
        /*
        for (int i = 0; i < xs[0].getLength(); i++) {
            loss -= xs1_.getValues()[i] * Math.log(p[i] + eps);
        }

         */
        for (int i = 0; i < shape0; i++) {
            for (int j = 0; j < shape1; j++) {
                loss -= xs1_.getValues()[i * shape1 + j] * Math.log(prob[i * shape1 + j] + eps);
            }
            System.out.println(loss);
        }
        loss /= shape0;

        return new Tensor[]{new Tensor(loss)};
    }

    @Override
    public Variable[] backward(Variable... gys) {

        double[] gx = new double[inputs[0].getLength()];

        // Compute softmax and gradients
        for (int i = 0; i < gx.length; i++) {
            gx[i] = prob[i] - xs1_.getValues()[i];
        }

        // Scale gradient by batch size
        double scale = gys[0].getValues()[0] / shape0;
        for (int i = 0; i < gx.length; i++) {
            gx[i] *= scale;
        }

        return new Variable[]{new Variable(gx).reshape(inputs[0].getShape()), null};
    }

}

