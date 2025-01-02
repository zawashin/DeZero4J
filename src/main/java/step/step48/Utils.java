package step.step48;

import dezero4j.Variable;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Utils {

    public static int[] argMax(Variable x) {
        switch (x.getRank()) {
            case 0:
                return new int[]{0};
            case 1:
                double max = x.getValues()[0];
                int argmax = 0;
                for (int i = 1; i < x.getLength(); i++) {
                    if (x.getValues()[i] > max) {
                        max = x.getValues()[i];
                        argmax = i;
                    }
                }
                return new int[]{argmax};
            case 2:
                int shape0 = x.getShape()[0];
                int shape1 = x.getShape()[1];
                double[] maxs;
                int[] argmaxs;
                maxs = new double[shape0];
                argmaxs = new int[shape0];
                for (int i = 0; i < shape0; i++) {
                    maxs[i] = x.getValues()[i * shape1];
                    argmaxs[i] = 0;
                    for (int j = 1; j < shape1; j++) {
                        if (x.getValues()[i * shape1 + j] > maxs[i]) {
                            maxs[i] = x.getValues()[i * shape1 + j];
                            argmaxs[i] = j;
                        }
                    }
                }
                return argmaxs;
            default:
                System.err.println("Rank " + x.getRank() + " is not supported.");
                throw new IllegalArgumentException("Rank " + x.getRank() + " is not supported.");
        }
    }
}
