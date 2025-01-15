package step.step49;

import dezero4j.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class MiniBatch {

    public static Variable[][] create(double[][] x, double[][] t, int numBatches) {
        Random random = new Random(System.currentTimeMillis());

        boolean vector = (t.length == 1);
        if (x.length != t.length && !vector) {
            throw new IllegalArgumentException("x and t must have the same length.");
        } else if (x.length != t[0].length) {
            throw new IllegalArgumentException("x and t must have the same length.");
        }

        int numData = x.length;
        if (numBatches <= 0 || numBatches > numData) {
            throw new IllegalArgumentException("N must be greater than 0 and less than or equal to the length of x.");
        }

        List<List<double[]>> xList = new ArrayList<>();
        List<List<double[]>> tList = new ArrayList<>();
        for (int i = 0; i < numBatches; i++) {
            xList.add(new ArrayList<>());
            tList.add(new ArrayList<>());
        }
        // Shuffle the indices
        for (int i = 0; i < numData; i++) {
            int idx = random.nextInt(numBatches);
            xList.get(idx).add(x[i]);
            if (vector) {
                tList.get(idx).add(new double[]{t[0][i]});

            } else {
                tList.get(idx).add(t[i]);
            }
        }
        double[][][] xArray = new double[numBatches][][];
        double[][][] tArray = new double[numBatches][][];

        for (int i = 0; i < numBatches; i++) {
            xArray[i] = new double[xList.get(i).size()][x[0].length];
            tArray[i] = new double[tList.get(i).size()][x[0].length];
        }
        for (int i = 0; i < numBatches; i++) {
            for (int j = 0; j < xList.get(i).size(); j++) {
                xArray[i][j] = xList.get(i).get(j);
                tArray[i][j] = tList.get(i).get(j);
            }
        }

        // Create mini-batches
        Variable[][] miniBatches = new Variable[numBatches][2];
        if (vector) {
            for (int i = 0; i < numBatches; i++) {
                double[] t_ = new double[tArray[i].length];
                for (int j = 0; j < t_.length; j++) {
                    t_[j] = tArray[i][j][0];
                }
                miniBatches[i][0] = new Variable(xArray[i]);
                miniBatches[i][1] = new Variable(t_);
            }
        } else {
            for (int i = 0; i < numBatches; i++) {
                miniBatches[i][0] = new Variable(xArray[i]);
                miniBatches[i][1] = new Variable(tArray[i]);
            }
        }

        return miniBatches;
    }


    public static Variable[][] create(double[][] x, double[] t, int numBatches) {
        return create(x, new double[][]{t}, numBatches);
    }

    public static void main(String[] args) {
        int numData = 300;
        int numBatches = 5;

        // Generate random data for x and t
        Random random = new Random();
        double[][] x = new double[numData][2];
        double[] t = new double[numData];

        for (int i = 0; i < numData; i++) {
            x[i][0] = random.nextDouble(); // Random value between 0 and 100
            x[i][1] = random.nextDouble();
            t[i] = random.nextInt(10); // Random value between 0 and 10
        }

        // Create mini-batches
        Variable[][] miniBatches = create(x, t, numBatches);

        // Print results
        for (int i = 0; i < miniBatches.length; i++) {
            System.out.println("Mini-batch " + (i + 1) + ":");
            Variable[] batch = miniBatches[i];
            System.out.println(batch[0]);
            System.out.println(batch[1]);
            System.out.println();
        }
    }
}
