package dezero4jv1.step.step39;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Tensor implements Cloneable, Serializable {
    protected int num1 = 1;
    protected int num2 = 1;
    protected int num3 = 1;
    protected int num4 = 1;
    protected int num2x3x4 = num2 * num3 * num4;
    protected int num3x4 = num3 * num4;
    protected int rank;
    protected int length;
    protected double[] values;
    protected int[] shape;
    public static final int RANK_MAX = 4;
    public static final String FORMAT = "%e";//"%7.3e";
    protected static final String[] ERROR_MESSAGE = new String[]{
            "Tensor Order is not 0th.",
            "Tensor Order is not 1st.",
            "Tensor Order is not 2nd.",
            "Tensor Order is not 3th.",
            "Tensor Order is not 4th.",
            "Over " + RANK_MAX + " rank Tensor is not implemented.",
            "Data Length is not correct"
    };
    protected static final String ERROR_RANK = ERROR_MESSAGE[RANK_MAX + 1];
    protected static final String ERROR_LENGTH = ERROR_MESSAGE[RANK_MAX + 2];

    public Tensor(double scalar) {
        rank = 0;
        shape = new int[1];
        shape[0] = 1;
        length = num1 * num2 * num3 * num4;
        values = new double[length];
        values[0] = scalar;
    }

    public Tensor(double[] array1d) {
        if (array1d.length == 1) {
            rank = 0;
            shape = new int[1];
            shape[0] = 1;
        } else {
            rank = 1;
            shape = new int[1];
            shape[0] = array1d.length;
            num1 = array1d.length;
        }
        length = num1 * num2 * num3 * num4;
        values = new double[length];
        values = array1d.clone();
    }

    public Tensor(double[][] array2d) {
        rank = 2;
        num1 = array2d.length;
        num2 = array2d[0].length;
        shape = new int[]{num1, num2};
        length = num1 * num2 * num3 * num4;
        values = new double[length];
        int n = 0;
        for (int i = 0; i < num1; i++) {
            for (int j = 0; j < num2; j++) {
                values[n++] = array2d[i][j];
            }
        }
        if( length != n) {
            throw new RuntimeException(ERROR_LENGTH);
        }
    }

    public Tensor(double[][][] array3d) {
        rank = 3;
        num1 = array3d.length;
        num2 = array3d[0].length;
        num3 = array3d[0][0].length;
        shape = new int[]{num1, num2, num3};
        num2x3x4 = num2 * num3 * num4;
        num3x4 = num3 * num4;
        length = num1 * num2 * num3 * num4;
        values = new double[length];
        int n = 0;
        for (int i = 0; i < num1; i++) {
            for (int j = 0; j < num2; j++) {
                for (int k = 0; k < num3; k++) {
                    values[n++] = array3d[i][j][k];
                }
            }
        }
        if( length != n) {
            throw new RuntimeException(ERROR_LENGTH);
        }
    }

    public Tensor(double[][][][] array4d) {
        rank = 4;
        num1 = array4d.length;
        num2 = array4d[0].length;
        num3 = array4d[0][0].length;
        num4 = array4d[0][0][0].length;
        shape = new int[]{num1, num2, num3, num4};
        num2x3x4 = num2 * num3 * num4;
        num3x4 = num3 * num4;
        length = num1 * num2 * num3 * num4;
        values = new double[length];
        int n = 0;
        for (int i = 0; i < num1; i++) {
            for (int j = 0; j < num2; j++) {
                for (int k = 0; k < num3; k++) {
                    for (int l = 0; l < num4; l++) {
                        values[n++] = array4d[i][j][k][l];
                    }
                }
            }
        }
        if( length != n) {
            throw new RuntimeException(ERROR_LENGTH);
        }
    }

    public Tensor(Tensor tensor) {
        values = tensor.values.clone();
        shape = tensor.shape.clone();
        rank = tensor.rank;
        num1 = tensor.num1;
        num2 = tensor.num2;
        num3 = tensor.num3;
        num4 = tensor.num4;
        num2x3x4 = num2 * num3 * num4;
        num3x4 = num3 * num4;
        length = tensor.length;
    }

    public Tensor(double[] values, int[] shape) {
        rank = shape.length;
        this.values = values.clone();
        this.shape = shape.clone();
        if (shape[0] == 1 && rank == 1) {
            rank = 0;
        }
        switch (rank) {
            case 0:
                break;
            case 1:
                num1 = shape[0];
                break;
            case 2:
                num1 = shape[0];
                num2 = shape[1];
                break;
            case 3:
                num1 = shape[0];
                num2 = shape[1];
                num3 = shape[2];
                break;
            case 4:
                num1 = shape[0];
                num2 = shape[1];
                num3 = shape[2];
                num4 = shape[3];
                break;
            default:
                throw new RuntimeException(ERROR_RANK);

        }
        num2x3x4 = num2 * num3 * num4;
        num3x4 = num3 * num4;
        length = values.length;
        if( length != num1*num2x3x4) {
            throw new RuntimeException(ERROR_LENGTH);
        }
    }

    public int getRank() {
        return rank;
    }

    public int getLength() {
        return length;
    }

    public int[] getShape() {
        return shape;
    }

    public void setShape(int[] shape) {
        int len = shape[0];
        for (int i = 1; i < shape.length; i++) {
            len *= shape[i];
        }
        if (len != length) {
            throw new RuntimeException(ERROR_LENGTH);
        }
        double[] data = new double[len];
        System.arraycopy(values, 0, data, 0, length);

        rank = shape.length;
        this.shape = shape.clone();
        if (shape[0] == 1 && rank == 1) {
            rank = 0;
        }
        int n = 0;
        switch (rank) {
            case 0:
                break;
            case 1:
                num1 = shape[0];
                num2x3x4 = num2 * num3 * num4;
                num3x4 = num3 * num4;
                for (int i = 0; i < num1; i++) {
                    values[n++] = data[index(i)];
                }
                break;
            case 2:
                num1 = shape[0];
                num2 = shape[1];
                num2x3x4 = num2 * num3 * num4;
                num3x4 = num3 * num4;
                for (int i = 0; i < num1; i++) {
                    for (int j = 0; j < num2; j++) {
                        values[n++] = data[index(i, j)];
                    }
                }
                break;
            case 3:
                num1 = shape[0];
                num2 = shape[1];
                num3 = shape[2];
                num2x3x4 = num2 * num3 * num4;
                num3x4 = num3 * num4;
                for (int i = 0; i < num1; i++) {
                    for (int j = 0; j < num2; j++) {
                        for (int k = 0; k < num3; k++) {
                            values[n++] = data[index(i, j, k)];
                        }
                    }
                }
                break;
            case 4:
                num1 = shape[0];
                num2 = shape[1];
                num3 = shape[2];
                num4 = shape[3];
                num2x3x4 = num2 * num3 * num4;
                num3x4 = num3 * num4;
                for (int i = 0; i < num1; i++) {
                    for (int j = 0; j < num2; j++) {
                        for (int k = 0; k < num3; k++) {
                            for (int l = 0; l < num4; l++) {
                                values[n++] = data[index(i, j, k, l)];
                            }
                        }
                    }
                }
                break;
            default:
                throw new RuntimeException(ERROR_RANK);

        }
    }

    public double[] getValues() {
        return values;
    }

    public Tensor clone() {
        Tensor clone;
        try {
            clone = (Tensor) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.values = this.values.clone();
        clone.shape = this.shape.clone();
        return clone;
    }

    public String toString() {
        return TensorUtils.toString(this);
    }

    public int index(int... indexes) {
        return TensorUtils.index(this, indexes);
    }

    public int index(int i, int j) {
        return TensorUtils.index(this, i, j);
    }

    public int index(int i, int j, int k) {
        return TensorUtils.index(this, i, j, k);
    }

    public int index(int i, int j, int k, int l) {
        return TensorUtils.index(this, i, j, k, l);
    }

    public double getValue(int... indices) {
        return TensorUtils.getValue(this, indices);
    }

    public double getValue(int i, int j) {
        return TensorUtils.getValue(this, i, j);
    }

    public double getValue(int i, int j, int k) {
        return TensorUtils.getValue(this, i, j, k);
    }

    public double getValue(int i, int j, int k, int l) {
        return TensorUtils.getValue(this, i, j, k, l);
    }

    public void setValue(double value, int... indices) {
        TensorUtils.setValue(this, value, indices);
    }

    public void setValue(double value) {
        TensorUtils.setValue(this, value);
    }

    public void setValue(double value, int i) {
        values[i] = value;
    }

    public void setValue(double value, int i, int j) {
        TensorUtils.setValue(this, value, i, j);
    }

    public void setValue(double value, int i, int j, int k) {
        TensorUtils.setValue(this, value, i, j, k);
    }

    public void setValue(double value, int i, int j, int k, int l) {
        TensorUtils.setValue(this, value, i, j, k, l);
    }

    public int[] indexes(int index) {
        return TensorUtils.indexes(this, index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tensor tensor)) return false;

        if (rank != tensor.rank) return false;
        if (length != tensor.length) return false;
        if (num1 != tensor.num1) return false;
        if (num2 != tensor.num2) return false;
        if (num3 != tensor.num3) return false;
        if (num4 != tensor.num4) return false;
        if (num2x3x4 != tensor.num2x3x4) return false;
        if (num3x4 != tensor.num3x4) return false;
        return Arrays.equals(values, tensor.values);
    }

    @Override
    public int hashCode() {
        int result = rank;
        result = 31 * result + length;
        result = 31 * result + Arrays.hashCode(values);
        result = 31 * result + Arrays.hashCode(shape);
        result = 31 * result + FORMAT.hashCode();
        result = 31 * result + num1;
        result = 31 * result + num2;
        result = 31 * result + num3;
        result = 31 * result + num4;
        result = 31 * result + num2x3x4;
        result = 31 * result + num3x4;
        return result;
    }

    public void plusAssign(Tensor other) {
        double[] x0 = values;
        double[] x1 = other.values;
        for (int i = 0; i < length; i++) {
            x0[i] += x1[i];
        }
    }

    public void minusAssign(Tensor other) {
        double[] x0 = values;
        double[] x1 = other.values;
        for (int i = 0; i < length; i++) {
            x0[i] -= x1[i];
        }
    }

    public Tensor plus(Tensor other) {
        double[] x0 = values;
        double[] x1 = other.values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = x0[i] + x1[i];
        }
        return new Tensor(y, shape);
    }

    public Tensor minus(Tensor other) {
        double[] xArray0 = values;
        double[] x1 = other.values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = xArray0[i] - x1[i];
        }
        return new Tensor(y, shape);
    }

    public Tensor times(Tensor other) {
        double[] xArray0 = values;
        double[] xArray1 = other.values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = xArray0[i] * xArray1[i];
        }
        return new Tensor(y, shape);

    }

    public Tensor div(Tensor other) {
        double[] x0 = values;
        double[] x1 = other.values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = x0[i] / x1[i];
        }
        return new Tensor(y, shape);
    }

    public Tensor neg() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < x0.length; i++) {
            y[i] = -x0[i];
        }
        return new Tensor(y, shape);
    }

    public Tensor cos() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.cos(x0[i]);
        }
        return new Tensor(y, shape);
    }

    public Tensor sin() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.sin(x0[i]);
        }
        return new Tensor(y, shape);
    }

    public Tensor tanh() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.tanh(x0[i]);
        }
        return new Tensor(y, shape);
    }

    public Tensor exp() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.exp(x0[i]);
        }
        return new Tensor(y, shape);
    }

    public Tensor log() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.log(x0[i]);
        }
        return new Tensor(y, shape);
    }

    public Tensor power(double index) {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.pow(x0[i], index);
        }
        return new Tensor(y, shape);
    }

    public Tensor reshape(int[] shape) {
        return new Tensor(values, shape);
    }
}