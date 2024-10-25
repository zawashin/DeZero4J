package dezero4jv1.step.step43;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class TensorOperator {

    public static Tensor plus(Tensor... xs) {
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = x0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = x0[i] + x1[i];
        }
        return new Tensor(values, xs[0].shape);
    }

    public static Tensor minus(Tensor... xs) {
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = x0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = x0[i] - x1[i];
        }
        return new Tensor(values, xs[0].shape);
    }

    public static Tensor times(Tensor... xs) {
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = x0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = x0[i] * x1[i];
        }
        return new Tensor(values, xs[0].shape);
    }

    public static Tensor div(Tensor... xs) {
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = xs[0].length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = x0[i] / x1[i];
        }
        return new Tensor(values, xs[0].shape);
    }

    public static Tensor neg(Tensor x) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < x0.length; i++) {
            values[i] = -x0[i];
        }
        return new Tensor(values, x.shape);
    }

    public static Tensor cos(Tensor x) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.cos(x0[i]);
        }
        return new Tensor(values, x.shape);
    }

    public static Tensor sin(Tensor x) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.sin(x0[i]);
        }
        return new Tensor(values, x.shape);
    }

    public static Tensor tanh(Tensor x) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.tanh(x0[i]);
        }
        return new Tensor(values, x.shape);
    }

    public static Tensor exp(Tensor x) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.exp(x0[i]);
        }
        return new Tensor(values, x.shape);
    }

    public static Tensor log(Tensor x) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.log(x0[i]);
        }
        return new Tensor(values, x.shape);
    }

    public static Tensor pow(Tensor x, double index) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.pow(x0[i], index);
        }
        return new Tensor(values, x.shape);
    }

    public static Tensor square(Tensor x) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = x0[i] * x0[i];
        }
        return new Tensor(values, x.shape);
    }


    public static Tensor reshape(Tensor x, int[] shape) {
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        if (x.getLength() != length) {
            throw new RuntimeException(TensorUtils.ERROR_LENGTH);
        }
        double[] values = new double[length];
        int n = 0;
        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                values[i * shape[1] + j] = x.getValues()[n++];
            }
        }
        return new Tensor(values, shape);
    }

    public static Tensor transpose(Tensor x) {
        int[] shape = new int[TensorUtils.RANK_MAX];
        Arrays.fill(shape, 1);
        shape[0] = x.getShape()[1];
        shape[1] = x.getShape()[0];
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        double[] values = new double[length];
        int n = 0;
        for(int i = 0; i < shape[0]; i++) {
            for(int j = 0; j < shape[1]; j++) {
                values[n++] = x.getValues()[j * shape[0] + i];
            }
        }
        return new Tensor(values, shape);
    }

    public static Tensor dot(Tensor... xs) {
        if(xs[0].getShape()[1] != xs[1].getShape()[0]) {
            System.out.println(Arrays.toString(xs[0].getShape()));
            System.out.println(Arrays.toString(xs[1].getShape()));
            throw new RuntimeException("Tensor Size Error");
        }
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = xs[0].getShape()[0] * xs[1].getShape()[1];
        int[] shape = new int[]{xs[0].getShape()[0], xs[1].getShape()[1]};
        double[] values = new double[length];
        int n2 = xs[0].getShape()[1];
        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                for (int k = 0; k < n2; k++) {
                    values[i * shape[1] + j] += x0[i * n2 + k] * x1[k * shape[1] + j];
                }
            }
        }
        return new Tensor(values, shape);
    }

    public static Tensor meanSquaredError(Tensor... xs) {
        if(xs[0].getLength() != xs[1].getLength()) {
            System.out.println(Arrays.toString(xs[0].getShape()));
            System.out.println(Arrays.toString(xs[1].getShape()));
            throw new RuntimeException("Tensor Size Error");
        }
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = xs[0].getLength();
        double value = 0.0;
        for (int i = 0; i < length; i++) {
            double dx = x0[i] - x1[i];
            value += dx * dx;
        }
        value /= length;
        return new Tensor(value);
    }
}
