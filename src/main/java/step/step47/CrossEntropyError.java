package step.step47;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class CrossEntropyError {

    public static void main(String[] args) {
        {

            // Example inputs
            Tensor logits = new Tensor(new double[][]{{2.0, 1.0, 0.1}});
            Tensor labels = new Tensor(new double[][]{{1.0, 0.0, 0.0}});

            // Forward pass
            double loss = forward(logits, labels);
            System.out.println("Cross-Entropy Loss: " + loss);

            // Backward pass
            Tensor gradients = backward(logits, labels);
            System.out.println("Gradients:");
            gradients.print();
        }
        {

            // Example inputs
            Tensor logits = new Tensor(new double[][]{{2.0, 1.0, 0.1}, {1.0, 2.0, 3.0}});
            Tensor labels = new Tensor(new double[][]{{1.0, 0.0, 0.0}, {0.0, 0.0, 1.0}});

            // Forward pass
            double loss = forward(logits, labels);
            System.out.println("Cross-Entropy Loss: " + loss);

            // Backward pass
            Tensor gradients = backward(logits, labels);
            System.out.println("Gradients:");
            gradients.print();
        }
    }

    // Forward pass: Cross-Entropy Loss
    public static double forward(Tensor logits, Tensor labels) {
        Tensor probabilities = softmax(logits);
        double loss = 0.0;
        for (int i = 0; i < labels.data.length; i++) {
            for (int j = 0; j < labels.data[i].length; j++) {
                if (labels.data[i][j] > 0) {
                    loss -= labels.data[i][j] * Math.log(probabilities.data[i][j]);
                }
            }
        }
        return loss / labels.data.length; // Average over batch size
    }

    // Backward pass: Gradient of Loss w.r.t logits
    public static Tensor backward(Tensor logits, Tensor labels) {
        Tensor probabilities = softmax(logits);
        Tensor gradients = probabilities.subtract(labels);
        gradients.divide(labels.data.length); // Average over batch size
        return gradients;
    }

    // Softmax function
    public static Tensor softmax(Tensor logits) {
        double[][] result = new double[logits.data.length][logits.data[0].length];
        for (int i = 0; i < logits.data.length; i++) {
            double maxLogit = Arrays.stream(logits.data[i]).max().orElse(0); // For numerical stability
            double sumExp = 0.0;
            for (int j = 0; j < logits.data[i].length; j++) {
                result[i][j] = Math.exp(logits.data[i][j] - maxLogit);
                sumExp += result[i][j];
            }
            for (int j = 0; j < logits.data[i].length; j++) {
                result[i][j] /= sumExp;
            }
        }
        return new Tensor(result);
    }
}

// Tensor class for unified handling of scalars, vectors, and matrices
class Tensor {
    double[][] data;

    // Constructor for scalar
    public Tensor(double value) {
        this.data = new double[][]{{value}};
    }

    // Constructor for vector
    public Tensor(double[] values) {
        this.data = new double[1][values.length];
        System.arraycopy(values, 0, this.data[0], 0, values.length);
    }

    // Constructor for matrix
    public Tensor(double[][] values) {
        this.data = values;
    }

    // Subtract another tensor
    public Tensor subtract(Tensor other) {
        double[][] result = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                result[i][j] = this.data[i][j] - other.data[i][j];
            }
        }
        return new Tensor(result);
    }

    // Divide by a scalar
    public void divide(double scalar) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] /= scalar;
            }
        }
    }

    // Print tensor
    public void print() {
        for (double[] row : data) {
            System.out.println(Arrays.toString(row));
        }
    }
}
