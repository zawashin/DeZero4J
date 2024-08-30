package dezero4j.step.step37;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Tensor2 implements Cloneable, Serializable {
    protected int num1 = 1;
    protected int num2 = 1;
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
            "Over 5th Order Tensor is not implemented.",
            "Data Length is not correct"
    };
    protected static final String ERROR_RANK = ERROR_MESSAGE[3];
    protected static final String ERROR_LENGTH = ERROR_MESSAGE[4];

    public Tensor2(double scalar) {
        rank = 0;
        shape = new int[1];
        shape[0] = 1;
        values = new double[length];
        values[0] = scalar;
    }

    public Tensor2(double[] array1d) {
        if (array1d.length == 1) {
            rank = 0;
            shape = new int[1];
            shape[0] = 1;
            length = num1 * num2;
        } else {
            rank = 1;
            shape = new int[1];
            shape[0] = array1d.length;
            num1 = array1d.length;
        }
        values = new double[length];
        values = array1d.clone();
    }

    public Tensor2(double[][] array2d) {
        rank = 2;
        num1 = array2d.length;
        num2 = array2d[0].length;
        shape = new int[]{num1, num2};
        length = num1 * num2;
        values = new double[length];
        int n = 0;
        for (int i = 0; i < num1; i++) {
            for (int j = 0; j < num2; j++) {
                values[n++] = array2d[i][j];
            }
        }
        assert n == length;
    }

    public Tensor2(Tensor2 tensor) {
        values = tensor.values.clone();
        shape = tensor.shape.clone();
        rank = tensor.rank;
        num1 = tensor.num1;
        num2 = tensor.num2;
        length = tensor.length;
        assert length == num1 * num2 : "Length Error";
    }

    public Tensor2(double[] values, int[] shape) {
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
            default:
                throw new RuntimeException("Over 5 rank tensor is not implemented");

        }
        length = values.length;
        assert values.length == num1 * num2: "Length Error";
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
        //this.values = values.clone();
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
                for (int i = 0; i < num1; i++) {
                    values[n++] = data[index(i)];
                }
                break;
            case 2:
                num1 = shape[0];
                num2 = shape[1];
                for (int i = 0; i < num1; i++) {
                    for (int j = 0; j < num2; j++) {
                        values[n++] = data[index(i, j)];
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

    public Tensor2 clone() {
        Tensor2 clone;
        try {
            clone = (Tensor2) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.values = this.values.clone();
        clone.shape = this.shape.clone();
        return clone;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();

        int n = 0;
        switch (rank) {
            case 0:
                buffer.append("[").append(values[0]).append("]");
                break;
            case 1:
                buffer.append("[");
                for (int i = 0; i < num1; i++) {
                    buffer.append(values[n++]);
                    if (i == num1 - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",");
                    }
                }
                break;
            case 2:
                buffer.append("[");
                for (int i = 0; i < num1; i++) {
                    if (i == 0) {
                        buffer.append("[");
                    } else {
                        buffer.append(" [");
                    }
                    for (int j = 0; j < num2; j++) {
                        buffer.append(values[n++]);
                        if (j == num2 - 1) {
                            buffer.append("]");
                        } else {
                            buffer.append(",");
                        }
                    }
                    if (i == num1 - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",\n");
                    }
                }
                //buffer.append("]");
                break;
            default:
                throw new RuntimeException(ERROR_RANK);
        }
        return buffer.toString();
    }

    public int index(int... indexes) {
        int index;
        switch (indexes.length) {
            case 1:
                if (rank != 1) {
                    throw new RuntimeException(ERROR_MESSAGE[1]);
                }
                index = indexes[0];
                break;
            case 2:
                if (rank != 2) {
                    throw new RuntimeException(ERROR_MESSAGE[2]);
                }
                index = indexes[0] * num2 + indexes[1];
                break;
            default:
                throw new RuntimeException("No tensor above the forth order has been implemented.");
        }
        return index;
    }

    public int index(int i, int j) {
        if (rank != 2) {
            throw new RuntimeException(ERROR_MESSAGE[2]);
        }
        return i * num2 + j;
    }

    public double getValue(int... indices) {
        int index = 0;
        switch (indices.length) {
            case 1:
                index = indices[0];
                break;
            case 2:
                assert rank == 2 : "Tensor is not Matrix";
                index = indices[0] * num2 + indices[1];
                break;
            default:
                throw new RuntimeException("No tensor above the forth order has been implemented.");
        }
        return index;
    }

    public double getValue(int i, int j) {
        if (rank != 2) {
            throw new RuntimeException("Tensor is not Matrix");
        }
        return values[i * num2 + j];
    }

    public void setValue(double value, int... indices) {
        int index = 0;
        switch (indices.length) {
            case 1:
                index = indices[0];
                break;
            case 2:
                assert rank == 2 : "Tensor is not Matrix";
                index = indices[0] * num2 + indices[1];
                break;
            default:
                throw new RuntimeException("No tensor above the forth order has been implemented.");
        }
        values[index] = value;
    }

    public void setValue(double value) {
        if (rank != 0) {
            throw new RuntimeException(ERROR_MESSAGE[0]);
        }
        values[0] = value;
    }

    public void setValue(double value, int i) {
        values[i] = value;
    }

    public void setValue(double value, int i, int j) {
        if (rank != 2) {
            throw new RuntimeException(ERROR_MESSAGE[2]);
        }
        values[i * num2 + j] = value;
    }

    public int[] indexes(int index) {
        // Stub
        int[] indeces = new int[rank];
        if (rank == 0) {
            indeces = new int[1];
            indeces[0] = 1;
        } else if (rank == 1) {
            indeces = new int[rank];
            indeces[0] = index;
        } else if (rank == 2) {
            indeces = new int[rank];
            indeces[0] = length / num2;
            indeces[1] = length % num2;
        } else {
            throw new RuntimeException();
        }
        return indeces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tensor2 tensor)) return false;

        if (rank != tensor.rank) return false;
        if (length != tensor.length) return false;
        if (num1 != tensor.num1) return false;
        if (num2 != tensor.num2) return false;
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
        return result;
    }

    public void plusAssign(Tensor2 other) {
        double[] x0 = values;
        double[] x1 = other.values;
        for (int i = 0; i < length; i++) {
            x0[i] += x1[i];
        }
    }

    public void minusAssign(Tensor2 other) {
        double[] x0 = values;
        double[] x1 = other.values;
        for (int i = 0; i < length; i++) {
            x0[i] -= x1[i];
        }
    }


    public Tensor2 plus(Tensor2 other) {
        double[] x0 = values;
        double[] x1 = other.values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = x0[i] + x1[i];
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 minus(Tensor2 other) {
        double[] xArray0 = values;
        double[] x1 = other.values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = xArray0[i] - x1[i];
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 multiply(Tensor2 other) {
        double[] xArray0 = values;
        double[] xArray1 = other.values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = xArray0[i] * xArray1[i];
        }
        return new Tensor2(y, shape);

    }

    public Tensor2 divide(Tensor2 other) {
        double[] x0 = values;
        double[] x1 = other.values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = x0[i] / x1[i];
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 neg() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < x0.length; i++) {
            y[i] = -x0[i];
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 cos() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.cos(x0[i]);
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 sin() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.sin(x0[i]);
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 tanh() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.tanh(x0[i]);
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 exp() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.exp(x0[i]);
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 log() {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.log(x0[i]);
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 power(double index) {
        double[] x0 = values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = Math.pow(x0[i], index);
        }
        return new Tensor2(y, shape);
    }

    public Tensor2 reshape(int[] shape) {
        return new Tensor2(values, shape);
    }
}