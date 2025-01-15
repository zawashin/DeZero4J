package step.step54;

import dezero4j.Variable;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class DropoutExample {
    private static Random random = new Random(System.currentTimeMillis());

    /**
     * Applies dropout to the input array.
     *
     * @param input       The input array (e.g., a layer's activations).
     * @param dropoutRate The probability of dropping an element (0.0 <= dropoutRate < 1.0).
     * @return The output array after applying dropout.
     */
    public static double[] applyDropout(double[] input, double dropoutRate) {
        if (dropoutRate < 0.0 || dropoutRate >= 1.0) {
            throw new IllegalArgumentException("dropoutRate must be in the range [0.0, 1.0).");
        }

        double scale = 1.0 / (1.0 - dropoutRate); // Scaling factor during training
        double[] output = new double[input.length];

        for (int i = 0; i < input.length; i++) {
            if (random.nextDouble() >= dropoutRate) {
                output[i] = input[i] * scale;
            } else {
                output[i] = 0.0;
            }
        }

        return output;
    }

    public static void main(String[] args) {
        // Example usage
        double[] input = {0.5, 1.2, -0.7, 0.3, 1.0};
        double dropoutRate = 0.3;

        double[] output = applyDropout(input, dropoutRate);

        System.out.println("Input:  ");
        for (double value : input) {
            System.out.printf("%.2f ", value);
        }

        System.out.println("\nOutput: ");
        for (double value : output) {
            System.out.printf("%.2f ", value);
        }
    }
}
