package dezero4jv1.step.step40;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class TensorUtils {
    public static final int RANK_MAX = 2;
    protected static final String[] ERROR_MESSAGE = new String[]{
            "Tensor Order is not 0th.",
            "Tensor Order is not 1st.",
            "Tensor Order is not 2nd.",
            "Over " + RANK_MAX + " Order Tensor is not implemented.",
            "Data Length is not correct",
            "Stub: Not implemented yet"
    };
    protected static final String ERROR_STUB = ERROR_MESSAGE[RANK_MAX + 3];
    protected static final String ERROR_LENGTH = ERROR_MESSAGE[RANK_MAX + 2];
    protected static final String ERROR_RANK = ERROR_MESSAGE[RANK_MAX + 1];

    /*
     * 2階のテンソルまでにしたので不要
     */
    public static Tensor reshapeSumBackward(Tensor gy, int[] shape, int axis) {
        if (gy != null) {
            throw new RuntimeException(TensorUtils.ERROR_STUB);
        }
        Tensor x = gy.clone();
        return new Tensor(x);
    }

    public static Tensor sum(Tensor x) {
        return sum(x, TensorUtils.RANK_MAX);
    }

    public static Tensor sum(Tensor x, int axis) {
        int length = 0;
        int[] sumShape = new int[TensorUtils.RANK_MAX];
        if (axis == TensorUtils.RANK_MAX) {
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
        if (axis == TensorUtils.RANK_MAX) {
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[0] += TensorUtils.getValue(x, i, j);
                }
            }
        } else if (axis == 0) {
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[j] += TensorUtils.getValue(x, i, j);
                }
            }
        } else if (axis == 1) {
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[i] += TensorUtils.getValue(x, i, j);
                }
            }
        }
        return new Tensor(values, sumShape);
    }

    /*
     // ChatGPTで修正する前
    public static Tensor broadcastTo(Tensor x, int[] shape) {
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        double[] values = new double[length];
        int m, n = 0;
        if (x.getShape()[0] == 1 && x.getShape()[1] == shape[1]) {
            // 行ベクトルのブロードキャスト
            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    values[n++] = x.getValue(0, j);
                }
            }
        } else if (x.getShape()[1] == 1 && x.getShape()[0] != 1) {
            // 列ベクトルのブロードキャスト
            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    values[n++] = x.getValue(i, 0);
                }
            }
        } else if (x.getShape()[0] == shape[0] && x.getShape()[1] == shape[1]) {
            // 行列がそのままの場合
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
        } else if (x.getShape()[0] == shape[0] && x.getShape()[1] != shape[1]) {
            // 行列の列方向へのブロードキャスト
            // 要る？
            n = x.getShape()[1];
            if( n % x.getShape()[1] != 0) {
                throw new RuntimeException(TensorUtils.ERROR_STUB);
            }

            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    values[i * shape[1] + j] = x.getValue(i, j % n);
                    //values[i * shape[1] + n++] = x.getValue(i, j / m);
                }
            }
        } else if (x.getShape()[1] == shape[1] && x.getShape()[0] != shape[0]) {
            // 行列の行方向へのブロードキャスト
            m = shape[0] / x.getShape()[0];
            if( m * x.getShape()[0] != shape[0]) {
                throw new RuntimeException(TensorUtils.ERROR_STUB);
            }
            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    values[i * shape[1] + j] = x.getValue(i % m, j);
                    //values[n++] = x.getValue(i % m, j);
                }
            }

        } else {
            m = shape[0] / x.getShape()[0];
            if( m * x.getShape()[0] != shape[0]) {
                throw new RuntimeException(TensorUtils.ERROR_STUB);
            }
            //n = shape[1] / x.getShape()[1];
            n = x.getShape()[1];
            if( n % x.getShape()[1] != 0) {
                throw new RuntimeException(TensorUtils.ERROR_STUB);
            }
            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    values[i * shape[1] + j] = x.getValue(i % m, j % n);
                }
            }
        }
        return new Tensor(values, shape);
    }
    */

    public static Tensor broadcastTo(Tensor x, int[] shape) {
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        double[] values = new double[length];
        // 行列はブロードキャストしない
        /*
        if (x.getShape()[0] != 1 && x.getShape()[1] != 1) {
            throw new RuntimeException(TensorUtils.ERROR_STUB);
        }

         */
        int n = 0;
        int[] xShape = x.getShape();
        int xShape0 = xShape[0];
        int xShape1 = xShape[1];
        int shape0 = shape[0];
        int shape1 = shape[1];

        for (int i = 0; i < shape0; i++) {
            for (int j = 0; j < shape1; j++) {
                int rowIndex = (xShape0 == 1 || xShape0 == shape0) ? Math.min(i, xShape0 - 1) : i % xShape0;
                int colIndex = (xShape1 == 1 || xShape1 == shape1) ? Math.min(j, xShape1 - 1) : j % xShape1;
                values[n++] = x.getValue(rowIndex, colIndex);
            }
        }
        return new Tensor(values, shape);
    }

    public static Tensor sumTo(Tensor x, int[] shape) {
        int length = shape[0];
        for (int i = 1; i < shape.length; i++) {
            length *= shape[i];
        }
        double[] values = new double[length];
        if (shape[0] != 1 && shape[1] == 1) {
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[i] += getValue(x, i, j);
                }
            }
        } else if (shape[0] == 1 && shape[1] != 1) {
            for (int i = 0; i < x.shape[0]; i++) {
                for (int j = 0; j < x.shape[1]; j++) {
                    values[j] += getValue(x, i, j);
                }
            }
        } else {
            // ベクトル対応
            // 行ベクトル
            if (x.shape[1] != 1) {
                for (int i = 0; i < x.shape[0]; i++) {
                    for (int j = 0; j < x.shape[1]; j++) {
                        values[i] += getValue(x, i, j);
                    }
                }
            }
            // 列ベクトル
            else if (x.shape[0] != 1) {
                for (int i = 0; i < x.shape[0]; i++) {
                    for (int j = 0; j < x.shape[1]; j++) {
                        values[j] += getValue(x, i, j);
                    }
                }
            } else {
                values[0] = x.getValues()[0];
            }
        }
        return new Tensor(values, shape);
    }

    // 2つの Tensor を相互的にブロードキャストするサイズを求める
    // ※Tensorは四則演算の際などに自動的にブロードキャストが行われないため明示的にこの関数を利用する
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

    public void setValue(Tensor tensor, double value, int i) {
        tensor.values[i] = value;
    }

    public static double getValue(Tensor tensor, int i) {
        return tensor.values[i];
    }

    public static double getValue(Tensor tensor, int i, int j) {
        return tensor.values[i * tensor.shape[1] + j];
    }

    public static void setValue(Tensor tensor, double value) {
        tensor.values[0] = value;
    }

    public static void setValue(Tensor tensor, double value, int i, int j) {
        tensor.values[i * tensor.shape[1] + j] = value;
    }

}
