package dezero4j.step.step34.step39;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class TensorOperator {
    public static final int RANK_MAX = 2;
    protected static final String[] ERROR_MESSAGE = new String[]{
            "Tensor Order is not 0th.",
            "Tensor Order is not 1st.",
            "Tensor Order is not 2nd.",
            "Over " + RANK_MAX + " Order Tensor is not implemented.",
            "Data Length is not correct",
            "Stub: Not implemented yet"
    };
    protected static final String ERROR_RANK = ERROR_MESSAGE[RANK_MAX + 1];
    protected static final String ERROR_LENGTH = ERROR_MESSAGE[RANK_MAX + 2];
    protected static final String ERROR_STUB = ERROR_MESSAGE[RANK_MAX + 3];

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

    public static Tensor reshape(Tensor x, int[] shape) {
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        if(x.getLength() != length) {
            throw new RuntimeException(TensorOperator.ERROR_LENGTH);
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
        double[] values = x.getValues().clone();
        int[] shape = new int[RANK_MAX];
        Arrays.fill(shape, 1);
        shape[0] = x.shape[1];
        shape[1] = x.shape[0];
        return new Tensor(values, shape);
    }

    public static Tensor sum(Tensor x) {
        return sum(x, RANK_MAX);
    }

    public static Tensor sum(Tensor x, int axis) {
        int length = 0;
        int[] sumShape = new int[RANK_MAX];
        if (axis == RANK_MAX) {
            Arrays.fill(sumShape, 1);
        } else if (axis == 0) {
            sumShape[0] = 1;
            sumShape[1] = x.getShape()[1];
        } else if (axis == 1) {
            sumShape[0] = x.getShape()[0];
            sumShape[1] = 1;
        }
        length = sumShape[0];
        for (int i = 1; i < x.shape.length; i++) {
            length *= sumShape[i];
        }
        double[] values = new double[length];
        if (axis == RANK_MAX) {
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[0] += getValue(x, i, j);
                }
            }
        } else if (axis == 0) {
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[j] += getValue(x, i, j);
                }
            }
        } else if (axis == 1) {
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[i] += getValue(x, i, j);
                }
            }
        }
        return new Tensor(values, sumShape);
    }


    public static Tensor broadcastTo(Tensor x, int[] shape) {
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        double[] values = new double[length];
        int n = 0;
        if (x.getShape()[0] == 1 && x.getShape()[1] == shape[1]) {
            // 行ベクトルのブロードキャスト
            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    values[n++] = x.getValue(0, j);
                }
            }
        } else if (x.getShape()[1] == 1 && x.getShape()[1] == shape[1]) {
            // 列ベクトルのブロードキャスト
            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    values[n++] = x.getValue(i, 0);
                }
            }
        } else if (x.getShape()[0] == shape[0] && x.getShape()[1] == shape[1]) {
            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    values[n++] = x.getValue(i, j);
                }
            }
        } else if (x.getShape()[0] == 1 && x.getShape()[1] == 1) {
            // スカラーのブロードキャスト
            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    values[n++] = x.getValue(0, 0);
                }
            }
        } else {
            throw new RuntimeException(ERROR_STUB);
        }
        return new Tensor(values, shape);
    }

    public static Tensor sumTo(Tensor x, int[] shape) {
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        double[] values = new double[length];
        if (shape[0] != 1) {
            System.out.println(Arrays.toString(shape));
            System.out.println(Arrays.toString(x.getShape()));
            System.out.println(shape[1] + "  " + x.getShape()[1]);
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[i] += getValue(x, i, j);
                }
            }
        } else {
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[i] += getValue(x, i, j);
                }
            }
        }
        return new Tensor(values, shape);
    }

    // 2つの Tensor を相互的にブロードキャストする
    // ※Tensorは四則演算の際などに自動的にブロードキャストが行われないため明示的にこの関数を利用する
    public static  Tensor[] broadcastTo(Tensor x0, Tensor x1) {
        Tensor[] xs = new Tensor[2];
        int[] shape = new int[RANK_MAX];
        for (int i = 0; i < RANK_MAX; i++) {
            shape[i] = Math.max(x0.shape[i], x1.shape[i]);
        }
        //x0 = broadcastTo(x0, shape);
        //x1 = broadcastTo(x1, shape);
        xs[0] = broadcastTo(x0, shape);
        xs[1] = broadcastTo(x1, shape);
        return xs;
    }

    public static int[] broadcastShape(Tensor x0, Tensor x1) {
        int[] shape = new int[RANK_MAX];
        for (int i = 0; i < RANK_MAX; i++) {
            shape[i] = Math.max(x0.shape[i], x1.shape[i]);
        }
        return shape;
    }

    public static int[] broadcastShape(Tensor... xs) {
        int[] shape = new int[RANK_MAX];
        for (int i = 0; i < RANK_MAX; i++) {
            shape[i] = Math.max(xs[0].shape[i], xs[1].shape[i]);
        }
        return shape;
    }


    /*
     * 2階のテンソルまでにしたので不要
    public static Tensor reshapeSumBackward(Tensor gy, int[] shape, int axis) {
        Tensor x = gy.clone();
        return new Tensor(x);
    }

     */

    public static String toString(Tensor tensor) {
        StringBuilder buffer = new StringBuilder();

        int n = 0;
        switch (tensor.rank) {
            case 0:
                buffer.append("[").append(tensor.values[0]).append("]");
                break;
            case 1:
                buffer.append("[");
                if (tensor.shape[0] == 1) {
                    for (int i = 0; i < tensor.shape[1]; i++) {
                        buffer.append(tensor.values[n++]);
                        if (i == tensor.shape[1] - 1) {
                            buffer.append("]");
                        } else {
                            buffer.append(", ");
                        }
                    }
                } else {
                    for (int i = 0; i < tensor.shape[0]; i++) {
                        if (i == 0) {
                            buffer.append("[");
                        } else {
                            buffer.append(" [");
                        }
                        for (int j = 0; j < tensor.shape[1]; j++) {
                            buffer.append(tensor.values[n++]);
                            if (j == tensor.shape[1] - 1) {
                                buffer.append("]");
                            } else {
                                buffer.append(", ");
                            }
                        }
                        if (i == tensor.shape[0] - 1) {
                            buffer.append("]");
                        } else {
                            buffer.append(",\n");
                        }
                    }
                }
                break;
            case 2:
                buffer.append("[");
                for (int i = 0; i < tensor.shape[0]; i++) {
                    if (i == 0) {
                        buffer.append("[");
                    } else {
                        buffer.append(" [");
                    }
                    for (int j = 0; j < tensor.shape[1]; j++) {
                        buffer.append(tensor.values[n++]);
                        if (j == tensor.shape[1] - 1) {
                            buffer.append("]");
                        } else {
                            buffer.append(", ");
                        }
                    }
                    if (i == tensor.shape[0] - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",\n");
                    }
                }
                break;
            default:
                throw new RuntimeException(ERROR_RANK);
        }
        return buffer.toString();
    }

    public static int index(Tensor tensor, int i, int j) {
        return i * tensor.shape[1] + j;
    }

    public static double getValue(Tensor tensor, int i) {
        return tensor.values[i];
    }

    public static double getValue(Tensor tensor, int i, int j) {
        return tensor.values[i * tensor.shape[1] + j];
    }

    public static void setValue(Tensor tensor, double value) {
        if (tensor.rank != 0) {
            throw new RuntimeException(ERROR_MESSAGE[0]);
        }
        tensor.values[0] = value;
    }

    public void setValue(Tensor tensor, double value, int i) {
        tensor.values[i] = value;
    }

    public static void setValue(Tensor tensor, double value, int i, int j) {
        tensor.values[i * tensor.shape[1] + j] = value;
    }

}
