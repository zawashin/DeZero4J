package step.step49;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class MiniBatchOriginal {

    public static List<List<double[][]>> createMiniBatches(double[][] x, double[][] t, int numBatches) {
        if (x.length != t.length) {
            throw new IllegalArgumentException("x and t must have the same length.");
        }

        int M = x.length;
        if (numBatches <= 0 || numBatches > M) {
            throw new IllegalArgumentException("N must be greater than 0 and less than or equal to the length of x.");
        }

        // Pair x and t together as indices
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            indices.add(i);
        }

        // Shuffle the indices
        Collections.shuffle(indices);

        // Create mini-batches
        List<List<double[][]>> miniBatches = new ArrayList<>();
        int batchSize = M / numBatches;
        int remainder = M % numBatches;
        int start = 0;

        for (int i = 0; i < numBatches; i++) {
            int end = start + batchSize + (i < remainder ? 1 : 0); // Distribute remainder evenly
            List<double[][]> batch = new ArrayList<>();
            for (int j = start; j < end; j++) {
                int idx = indices.get(j);
                double[][] pair = {x[idx], t[idx]};
                batch.add(pair);
            }
            miniBatches.add(batch);
            start = end;
        }

        return miniBatches;
    }


    public static List<List<double[][]>> createMiniBatches(double[][] x, double[] t, int numBatches) {
        if (x.length != t.length) {
            throw new IllegalArgumentException("x and t must have the same length.");
        }

        int M = x.length;
        if (numBatches <= 0 || numBatches > M) {
            throw new IllegalArgumentException("N must be greater than 0 and less than or equal to the length of x.");
        }

        // Pair x and t together as indices
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            indices.add(i);
        }

        // Shuffle the indices
        Collections.shuffle(indices);

        // Create mini-batches
        List<List<double[][]>> miniBatches = new ArrayList<>();
        int batchSize = M / numBatches;
        int remainder = M % numBatches;
        int start = 0;

        for (int i = 0; i < numBatches; i++) {
            int end = start + batchSize + (i < remainder ? 1 : 0); // Distribute remainder evenly
            List<double[][]> batch = new ArrayList<>();
            for (int j = start; j < end; j++) {
                int idx = indices.get(j);
                double[][] pair = {x[idx], {t[idx]}};
                batch.add(pair);
            }
            miniBatches.add(batch);
            start = end;
        }

        return miniBatches;
    }

    public static void main(String[] args) {
        int M = 300;
        int numBatches = 5;

        // Generate random data for x and t
        Random random = new Random();
        double[][] x = new double[M][2];
        double[] t = new double[M];

        for (int i = 0; i < M; i++) {
            x[i][0] = random.nextDouble() * 100; // Random value between 0 and 100
            x[i][1] = random.nextDouble() * 100;
            t[i] = random.nextInt(10); // Random value between 0 and 10
        }

        // Create mini-batches
        List<List<double[][]>> miniBatches = createMiniBatches(x, t, numBatches);

        // Print results
        for (int i = 0; i < miniBatches.size(); i++) {
            System.out.println("Mini-batch " + (i + 1) + ":");
            List<double[][]> batch = miniBatches.get(i);
            for (double[][] pair : batch) {
                System.out.printf("x: [%.2f, %.2f], t: %.2f\n", pair[0][0], pair[0][1], pair[1][0]);
            }
            System.out.println();
        }
    }
}
