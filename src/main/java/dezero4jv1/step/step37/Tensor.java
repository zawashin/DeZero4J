package dezero4jv1.step.step37;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Tensor implements Cloneable, Serializable {
    protected int iMax = 1;
    protected int jMax = 1;
    protected int num3 = 1;
    protected int num4 = 1;
    protected int num2x3x4 = jMax * num3 * num4;
    protected int num3x4 = num3 * num4;
    public int rank;
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
            "Over 5th Order Tensor is not implemented.",
            "Data Length is not correct"
    };
    protected static final String ERROR_RANK = ERROR_MESSAGE[5];
    protected static final String ERROR_LENGTH = ERROR_MESSAGE[6];

    public Tensor(double scalar) {
        rank = 0;
        shape = new int[1];
        shape[0] = 1;
        length = iMax * jMax * num3 * num4;
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
            iMax = array1d.length;
        }
        length = iMax * jMax * num3 * num4;
        values = new double[length];
        values = array1d.clone();
    }

    public Tensor(double[][] array2d) {
        rank = 2;
        iMax = array2d.length;
        jMax = array2d[0].length;
        shape = new int[]{iMax, jMax};
        length = iMax * jMax * num3 * num4;
        values = new double[length];
        int n = 0;
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++) {
                values[n++] = array2d[i][j];
            }
        }
        assert n == length;
    }

    public Tensor(double[][][] array3d) {
        rank = 3;
        iMax = array3d.length;
        jMax = array3d[0].length;
        num3 = array3d[0][0].length;
        shape = new int[]{iMax, jMax, num3};
        num2x3x4 = jMax * num3 * num4;
        num3x4 = num3 * num4;
        length = iMax * jMax * num3 * num4;
        values = new double[length];
        int n = 0;
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++) {
                for (int k = 0; k < num3; k++) {
                    values[n++] = array3d[i][j][k];
                }
            }
        }
        assert length == iMax * jMax * num3 * num4 : "Length Error";
    }

    public Tensor(double[][][][] array4d) {
        rank = 4;
        iMax = array4d.length;
        jMax = array4d[0].length;
        num3 = array4d[0][0].length;
        num4 = array4d[0][0][0].length;
        shape = new int[]{iMax, jMax, num3, num4};
        num2x3x4 = jMax * num3 * num4;
        num3x4 = num3 * num4;
        length = iMax * jMax * num3 * num4;
        values = new double[length];
        int n = 0;
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++) {
                for (int k = 0; k < num3; k++) {
                    for (int l = 0; l < num4; l++) {
                        values[n++] = array4d[i][j][k][l];
                    }
                }
            }
        }
        assert length == iMax * jMax * num3 * num4 : "Length Error";
    }

    public Tensor(Tensor tensor) {
        values = tensor.values.clone();
        shape = tensor.shape.clone();
        rank = tensor.rank;
        iMax = tensor.iMax;
        jMax = tensor.jMax;
        num3 = tensor.num3;
        num4 = tensor.num4;
        num2x3x4 = jMax * num3 * num4;
        num3x4 = num3 * num4;
        length = tensor.length;
        assert length == iMax * jMax * num3 * num4 : "Length Error";
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
                iMax = shape[0];
                break;
            case 2:
                iMax = shape[0];
                jMax = shape[1];
                break;
            case 3:
                iMax = shape[0];
                jMax = shape[1];
                num3 = shape[2];
                break;
            case 4:
                iMax = shape[0];
                jMax = shape[1];
                num3 = shape[2];
                num4 = shape[3];
                break;
            default:
                throw new RuntimeException("Over 5 rank tensor is not implemented");

        }
        num2x3x4 = jMax * num3 * num4;
        num3x4 = num3 * num4;
        length = values.length;
        assert values.length == iMax * jMax * num3 * num4 : "Length Error";
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
                iMax = shape[0];
                num2x3x4 = jMax * num3 * num4;
                num3x4 = num3 * num4;
                for (int i = 0; i < iMax; i++) {
                    values[n++] = data[index(i)];
                }
                break;
            case 2:
                iMax = shape[0];
                jMax = shape[1];
                num2x3x4 = jMax * num3 * num4;
                num3x4 = num3 * num4;
                for (int i = 0; i < iMax; i++) {
                    for (int j = 0; j < jMax; j++) {
                        values[n++] = data[index(i, j)];
                    }
                }
                break;
            case 3:
                iMax = shape[0];
                jMax = shape[1];
                num3 = shape[2];
                num2x3x4 = jMax * num3 * num4;
                num3x4 = num3 * num4;
                for (int i = 0; i < iMax; i++) {
                    for (int j = 0; j < jMax; j++) {
                        for (int k = 0; k < num3; k++) {
                            values[n++] = data[index(i, j, k)];
                        }
                    }
                }
                break;
            case 4:
                iMax = shape[0];
                jMax = shape[1];
                num3 = shape[2];
                num4 = shape[3];
                num2x3x4 = jMax * num3 * num4;
                num3x4 = num3 * num4;
                for (int i = 0; i < iMax; i++) {
                    for (int j = 0; j < jMax; j++) {
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
        StringBuilder buffer = new StringBuilder();

        int n = 0;
        switch (rank) {
            case 0:
                buffer.append("[").append(values[0]).append("]");
                break;
            case 1:
                buffer.append("[");
                for (int i = 0; i < iMax; i++) {
                    buffer.append(values[n++]);
                    if (i == iMax - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",");
                    }
                }
                break;
            case 2:
                buffer.append("[");
                for (int i = 0; i < iMax; i++) {
                    if (i == 0) {
                        buffer.append("[");
                    } else {
                        buffer.append(" [");
                    }
                    for (int j = 0; j < jMax; j++) {
                        buffer.append(values[n++]);
                        if (j == jMax - 1) {
                            buffer.append("]");
                        } else {
                            buffer.append(",");
                        }
                    }
                    if (i == iMax - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",\n");
                    }
                }
                //buffer.append("]");
                break;
            case 3:
                for (int i = 0; i < iMax; i++) {
                    if (i == 0) {
                        buffer.append("[");
                    } else {
                        buffer.append(" [");
                    }
                    for (int j = 0; j < jMax; j++) {
                        for (int k = 0; k < num3; k++) {
                            buffer.append(values[n++]).append("\n");
                        }
                    }
                    if (i == iMax - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",\n");
                    }
                }
                break;
            case 4:
                for (int i = 0; i < iMax; i++) {
                    if (i == 0) {
                        buffer.append("[");
                    } else {
                        buffer.append(" [");
                    }
                    for (int j = 0; j < jMax; j++) {
                        for (int k = 0; k < num3; k++) {
                            for (int l = 0; l < num4; l++) {
                                buffer.append(values[n++]).append("\n");
                            }
                        }
                    }
                    if (i == iMax - 1) {
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
                index = indexes[0] * jMax + indexes[1];
                break;
            case 3:
                if (rank != 3) {
                    throw new RuntimeException(ERROR_MESSAGE[3]);
                }
                return indexes[0] * num2x3x4 + indexes[1] * num3x4 + indexes[1] * num4;
            case 4:
                if (rank != 4) {
                    throw new RuntimeException(ERROR_MESSAGE[4]);
                }
                return indexes[0] * num2x3x4 + indexes[1] * num3x4 + indexes[2] * num4 + indexes[3];
            default:
                throw new RuntimeException("No tensor above the forth order has been implemented.");
        }
        return index;
    }

    public int index(int i, int j) {
        if (rank != 2) {
            throw new RuntimeException(ERROR_MESSAGE[2]);
        }
        return i * jMax + j;
    }

    public int index(int i, int j, int k) {
        if (rank != 3) {
            throw new RuntimeException(ERROR_MESSAGE[3]);
        }
        return i * num2x3x4 + j * num3x4 + k * num4;
    }

    public int index(int i, int j, int k, int l) {
        assert rank == 4 : "Tensor is not 4th order";
        return i * num2x3x4 + j * num3x4 + k * num4 + l;
    }

    public double getValue(int... indices) {
        int index;
        switch (indices.length) {
            case 1:
                index = indices[0];
                break;
            case 2:
                assert rank == 2 : "Tensor is not Matrix";
                index = indices[0] * jMax + indices[1];
                break;
            case 3:
                assert rank == 3 : "Tensor is not 3rd order";
                return indices[0] * num2x3x4 + indices[1] * num3x4 + indices[1] * num4;
            case 4:
                assert rank == 4 : "Tensor is not 4th order";
                return indices[0] * num2x3x4 + indices[1] * num3x4 + indices[2] * num4 + indices[3];
            default:
                throw new RuntimeException("No tensor above the forth order has been implemented.");
        }
        return index;
    }

    public double getValue(int i, int j) {
        if (rank != 2) {
            throw new RuntimeException("Tensor is not Matrix");
        }
        return values[i * jMax + j];
    }

    public double getValue(int i, int j, int k) {
        if (rank != 3) {
            throw new RuntimeException("Tensor is not 3rd order");
        }
        return values[i * num2x3x4 + j * num3x4 + k * num4];
    }

    public double getValue(int i, int j, int k, int l) {
        assert rank == 4 : "Tensor is not 4th order";
        return values[i * num2x3x4 + j * num3x4 + k * num4 + l];
    }

    public void setValue(double value, int... indices) {
        int index;
        switch (indices.length) {
            case 1:
                index = indices[0];
                break;
            case 2:
                assert rank == 2 : "Tensor is not Matrix";
                index = indices[0] * jMax + indices[1];
                break;
            case 3:
                assert rank == 3 : "Tensor is not 3rd order";
                index = indices[0] * num2x3x4 + indices[1] * num3x4 + indices[1] * num4;
                break;
            case 4:
                assert rank == 4 : "Tensor is not 4th order";
                index = indices[0] * num2x3x4 + indices[1] * num3x4 + indices[2] * num4 + indices[3];
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
        values[i * jMax + j] = value;
    }

    public void setValue(double value, int i, int j, int k) {
        if (rank != 3) {
            throw new RuntimeException(ERROR_MESSAGE[3]);
        }
        values[i * num2x3x4 + j * num3x4 + k * num4] = value;
    }

    public void setValue(double value, int i, int j, int k, int l) {
        if (rank != 4) {
            throw new RuntimeException(ERROR_MESSAGE[4]);
        }
        values[i * num2x3x4 + j * num3x4 + k * num4 + l] = value;
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
            indeces[0] = length / num2x3x4;
            indeces[1] = (length % num2x3x4) / num3x4;
        } else if (rank == 3) {
            indeces = new int[rank];
            indeces[0] = length / num2x3x4;
            indeces[1] = (length % num2x3x4) / num3x4;
            indeces[2] = ((length % num2x3x4) % num3x4) / num4;
        } else if (rank == 4) {
            indeces = new int[rank];
            indeces[0] = length / num2x3x4;
            indeces[1] = (length % num2x3x4) / num3x4;
            indeces[2] = ((length % num2x3x4) % num3x4) / num4;
            indeces[3] = ((length % num2x3x4) % (num3x4)) % num4;
        } else {
            throw new RuntimeException();
        }
        return indeces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tensor tensor)) return false;

        if (rank != tensor.rank) return false;
        if (length != tensor.length) return false;
        if (iMax != tensor.iMax) return false;
        if (jMax != tensor.jMax) return false;
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
        result = 31 * result + iMax;
        result = 31 * result + jMax;
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