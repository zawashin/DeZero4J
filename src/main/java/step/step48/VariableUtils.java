package step.step48;

import dezero4j.Variable;
import tensor4j.Tensor;

public class VariableUtils {

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

    public static Variable toCategorical(Variable x, int n) {
        int shape0;
        double[][] values;
        switch (x.getRank()) {
            case 0:
                values = new double[1][n];
                values[0][(int) x.getValues()[0]] = 1.0;
                if ((int) x.getValues()[0] >= n) {
                    throw new IllegalArgumentException("n " + x.getRank() + " is  supported.");
                }
                return new Variable(values[0]);
            case 1:
                shape0 = x.getShape()[0];
                values = new double[shape0][n];
                for (int i = 0; i < shape0; i++) {
                    if ((int) x.getValues()[i] >= n) {
                        throw new IllegalArgumentException("n " + x.getRank() + " is  supported.");
                    }
                    values[i][(int) x.getValues()[i]] = 1.0;
                }
                return new Variable(values);
            default:
                throw new IllegalArgumentException("Rank " + x.getRank() + " is not supported.");
        }
    }

    // ソフトマックス関数 (forward)
    public static int[] softMaxAndArgMax(Variable x) {
        int shape0, shape1;
        int[][] indices;
        switch (x.getRank()) {
            case 0:
                shape0 = 1;
                shape1 = 1;
                break;
            case 1:
                shape0 = 1;
                shape1 = x.getShape()[0];
                break;
            case 2:
                shape0 = x.getShape()[0];
                shape1 = x.getShape()[1];
                break;
            default:
                throw new IllegalArgumentException("Rank " + x.getRank() + " is not supported.");
        }
        double[] values = new double[shape0 * shape1];
        indices = new int[shape0][shape1];
        for (int i = 0; i < shape0; i++) {
            for (int j = 0; j < shape1; j++) {
                indices[i][j] = i * shape1 + j;
            }
        }

        for (int i = 0; i < shape0; i++) {
            double max = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < shape1; j++) {
                if (x.getValues()[indices[i][j]] > max) {
                    max = x.getValues()[indices[i][j]];
                }
            }
            for (int j = 0; j < shape1; j++) {
                values[indices[i][j]] = Math.exp(x.getValues()[indices[i][j]] - max);
            }
            double sumExp = 0.0;
            for (int j = 0; j < shape1; j++) {
                int index = i * shape1 + j;
                values[index] = Math.exp(x.getValues()[index] - max);
                sumExp += values[index];
            }
            for (int j = 0; j < shape1; j++) {
                values[i * shape1 + j] /= sumExp;
            }
        }
        int[] argMax = new int[shape0];
        for (int i = 0; i < shape0; i++) {
            int arg = 0;
            double max = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < shape1; j++) {
                if (values[i * shape1 + j] > max) {
                    max = values[i * shape1 + j];
                    arg = j;
                }
            }
            argMax[i] = arg;
        }

        return argMax;
    }

    /*
    Softmax Output:
    [0.09003057317038046, 0.24472847105479764, 0.6652409557748218]
    [0.8437947344813395, 0.11419519938459449, 0.04201006613406605]
    Backward Output:
    [-0.014181709360981211, -0.014077035746963, 0.02825874510794424]
    [0.016725305314204297, -0.00915599600654278, -0.00756930930766152]
     */
    public static void main(String[] args) {
        // 入力データの例
        double[][] input = {
                {1.0, 2.0, 3.0},
                {3.0, 1.0, 0.0}
        };

        Variable x = new Variable(new Tensor(input));
        // Softmaxインスタンスの生成
        int[] y = VariableUtils.softMaxAndArgMax(x);
        System.out.println("Softmax Output:" + y);
    }
}
