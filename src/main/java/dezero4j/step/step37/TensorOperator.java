package dezero4j.step.step37;

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

    public static Tensor plus(Tensor x0, Tensor x1) {
        double[] xArray0 = x0.values;
        double[] xArray1 = x1.values;
        int length = xArray0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = xArray0[i] + xArray1[i];
        }
        return new Tensor(values, x0.shape);
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

    public static Tensor minus(Tensor x0, Tensor x1) {
        double[] xArray0 = x0.values;
        double[] xArray1 = x1.values;
        int length = xArray0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = xArray0[i] - xArray1[i];
        }
        return new Tensor(values, x0.shape);
    }

    public static Tensor multiply(Tensor... xs) {
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = x0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = x0[i] * x1[i];
        }
        return new Tensor(values, xs[0].shape);
    }


    public static Tensor multiply(Tensor x0, Tensor x1) {
        double[] xArray0 = x0.values;
        double[] xArray1 = x1.values;
        int length = xArray0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = xArray0[i] * xArray1[i];
        }
        return new Tensor(values, x0.shape);
    }

    public static Tensor divide(Tensor... xs) {
        double[] x0 = xs[0].values;
        double[] x1 = xs[1].values;
        int length = xs[0].length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = x0[i] / x1[i];
        }
        return new Tensor(values, xs[0].shape);
    }

    public static Tensor divide(Tensor x0, Tensor x1) {
        double[] xArray0 = x0.values;
        double[] xArray1 = x1.values;
        int length = xArray0.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = xArray0[i] / xArray1[i];
        }
        return new Tensor(values, x0.shape);
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

    public static Tensor power(Tensor x, double index) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.pow(x0[i], index);
        }
        return new Tensor(values, x.shape);
    }

    public static Tensor power(double index, Tensor x) {
        double[] x0 = x.values;
        int length = x.length;
        double[] values = new double[length];
        for (int i = 0; i < length; i++) {
            values[i] = Math.pow(x0[i], index);
        }
        return new Tensor(values, x.shape);
    }

}
