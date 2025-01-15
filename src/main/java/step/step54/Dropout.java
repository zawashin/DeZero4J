package step.step54;

import dezero4j.Variable;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Dropout {
    private static Random random = new Random(System.currentTimeMillis());

    public static void dropout(Variable x, double dropoutRate) {
        if (dropoutRate < 0.0 || dropoutRate >= 1.0) {
            throw new IllegalArgumentException("dropoutRate must be in the range [0.0, 1.0).");
        }

        double scale = 1.0 / (1.0 - dropoutRate); // Scaling factor during training
        double[] output = new double[x.getLength()];

        for (int i = 0; i < x.getLength(); i++) {
            if (random.nextDouble() >= dropoutRate) {
                x.getValues()[i] *= scale;
            } else {
                x.getValues()[i] = 0.0;
            }
        }

    }

    public static void main(String[] args) {
        // Example usage
        double[] input = {0.5, 1.2, -0.7, 0.3, 1.0};
        double dropoutRate = 0.3;
        Variable x = new Variable(input);

        System.out.println(x);
        dropout(x, dropoutRate);
        System.out.println(x);

    }
}
