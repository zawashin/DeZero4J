package dezero4jv1.step.step45;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class SoftmaxFunction {

    public static double[][] softmax(double[][] x, int axis) {
        if (axis != 0 && axis != 1) {
            throw new IllegalArgumentException("Axis must be 0 or 1.");
        }

        double[][] shiftedX = shiftValues(x, axis);
        double[][] expX = exponentiate(shiftedX);
        double[] sumExpX = sum(expX, axis);

        return div(expX, sumExpX, axis);
    }

    private static double[][] shiftValues(double[][] x, int axis) {
        double[][] shiftedX = new double[x.length][x[0].length];
        if (axis == 1) {
            for (int i = 0; i < x.length; i++) {
                double max = max(x[i]);
                for (int j = 0; j < x[i].length; j++) {
                    shiftedX[i][j] = x[i][j] - max;
                }
            }
        } else {
            for (int j = 0; j < x[0].length; j++) {
                double max = max(column(x, j));
                for (int i = 0; i < x.length; i++) {
                    shiftedX[i][j] = x[i][j] - max;
                }
            }
        }
        return shiftedX;
    }

    private static double[][] exponentiate(double[][] x) {
        double[][] expX = new double[x.length][x[0].length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                expX[i][j] = Math.exp(x[i][j]);
            }
        }
        return expX;
    }

    private static double[] sum(double[][] x, int axis) {
        double[] sum = new double[(axis == 0) ? x[0].length : x.length];
        if (axis == 1) {
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x[i].length; j++) {
                    sum[i] += x[i][j];
                }
            }
        } else {
            for (int j = 0; j < x[0].length; j++) {
                for (int i = 0; i < x.length; i++) {
                    sum[j] += x[i][j];
                }
            }
        }
        return sum;
    }

    private static double[][] div(double[][] x, double[] sum, int axis) {
        double[][] result = new double[x.length][x[0].length];
        if (axis == 1) {
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x[i].length; j++) {
                    result[i][j] = x[i][j] / sum[i];
                }
            }
        } else {
            for (int j = 0; j < x[0].length; j++) {
                for (int i = 0; i < x.length; i++) {
                    result[i][j] = x[i][j] / sum[j];
                }
            }
        }
        return result;
    }

    private static double max(double[] x) {
        double max = x[0];
        for (double v : x) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    private static double[] column(double[][] x, int col) {
        double[] column = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            column[i] = x[i][col];
        }
        return column;
    }

    public static void main(String[] args) {
        double[][] x = {
                {1.0, 2.0},
                {3.0, 4.0}
        };

        double[][] softmaxOnAxis0 = softmax(x, 0);
        double[][] softmaxOnAxis1 = softmax(x, 1);

        System.out.println("Softmax along axis 0:");
        printMatrix(softmaxOnAxis0);

        System.out.println("Softmax along axis 1:");
        printMatrix(softmaxOnAxis1);
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.printf("%.4f ", val);
            }
            System.out.println();
        }
    }
}
