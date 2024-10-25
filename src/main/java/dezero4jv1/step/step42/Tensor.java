package dezero4jv1.step.step42;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Tensor implements Cloneable, Serializable {
    protected int rank;
    protected int length;
    protected double[] values;
    protected int[] shape;

    {
        shape = new int[TensorUtils.RANK_MAX];
        Arrays.fill(shape, 1);
        length = shape[0] * shape[1];
    }

    public Tensor(double value) {
        rank = 0;
        values = new double[length];
        values[0] = value;
    }

    public Tensor(double[] array1d) {
        if (array1d.length == 1) {
            rank = 0;
        } else {
            rank = 1;
            shape[1] = array1d.length;
        }
        length = shape[0] * shape[1];
        values = array1d.clone();
    }

    public Tensor(double[][] array2d) {
        if (array2d.length == 1) {
            if (array2d[0].length == 1) {
                rank = 0;
            } else {
                // 行ベクトル
                rank = 1;
            }
        } else {
            if (array2d[0].length == 1) {
                // 列ベクトル
                rank = 1;
            } else {
                rank = 2;
            }
        }
        setShape(new int[]{array2d.length, array2d[0].length});
        length = shape[0] * shape[1];
        values = new double[length];
        int n = 0;
        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                values[n++] = array2d[i][j];
            }
        }
        if (length != n) {
            throw new RuntimeException(TensorUtils.ERROR_LENGTH);
        }
    }

    public Tensor(Tensor tensor) {
        values = tensor.values.clone();
        shape = tensor.shape.clone();
        rank = tensor.rank;
        length = tensor.length;
    }

    public Tensor(double[] values, int[] shape) {
        length = values.length;
        if (length != shape[0] * shape[1]) {
            throw new RuntimeException(TensorUtils.ERROR_LENGTH);
        }
        if (shape[0] == 1) {
            if (shape[1] == 1) {
                rank = 0;
            } else {
                rank = 1;
            }
        } else {
            if (shape[1] == 1) {
                rank = 1;
            } else {
                rank = 2;
            }
        }
        this.values = values.clone();
        this.shape = shape.clone();
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
        this.shape = shape.clone();
        if (shape.length == 1 && shape[0] != 1) {
            this.shape[0] = 1;
            this.shape[1] = shape[0];
        } else {
            System.arraycopy(shape, 0, this.shape, 0, shape.length);
        }
        if (this.shape[0] == 1) {
            if (shape[1] == 1) {
                rank = 0;
            } else {
                rank = 1;
            }
        } else {
            rank = 2;
        }
    }

    public double[] getValues() {
        return values;
    }

    public Tensor clone() {
        Tensor clone;
        try {
            clone = (Tensor) super.clone();
            clone.values = this.values.clone();
            clone.shape = this.shape.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    public String toString() {
        return TensorUtils.toString(this);
    }

    public int index(int i, int j) {
        return TensorUtils.index(this, i, j);
    }

    public double getValue(int i, int j) {
        return TensorUtils.getValue(this, i, j);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tensor tensor)) return false;
        if (rank != tensor.rank) return false;
        if (length != tensor.length) return false;
        return Arrays.equals(values, tensor.values) && Arrays.equals(shape, tensor.shape);
    }

    @Override
    public int hashCode() {
        int result = rank;
        result = 31 * result + length;
        result = 31 * result + Arrays.hashCode(values);
        result = 31 * result + Arrays.hashCode(shape);
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

    public void timesAssign(Tensor other) {
        double[] x0 = values;
        double[] x1 = other.values;
        for (int i = 0; i < length; i++) {
            x0[i] *= x1[i];
        }
    }

    public void divAssign(Tensor other) {
        double[] x0 = values;
        double[] x1 = other.values;
        for (int i = 0; i < length; i++) {
            x0[i] /= x1[i];
        }
    }

    public Tensor plus(Tensor other) {
        return TensorOperator.plus(this, other);
    }

    public Tensor minus(Tensor other) {
        return TensorOperator.minus(this, other);
    }

    public Tensor times(Tensor other) {
        return TensorOperator.times(this, other);
    }

    public Tensor div(Tensor other) {
        return TensorOperator.div(this, other);
    }

    public Tensor dot(Tensor other) {
        return TensorOperator.dot(this, other);
    }

    public Tensor neg() {
        return TensorOperator.neg(this);
    }

    public Tensor cos() {
        return TensorOperator.cos(this);
    }

    public Tensor sin() {
        return TensorOperator.sin(this);
    }

    public Tensor tanh() {
        return TensorOperator.tanh(this);
    }

    public Tensor exp() {
        return TensorOperator.exp(this);
    }

    public Tensor log() {
        return TensorOperator.log(this);
    }

    public Tensor pow(double index) {
        return TensorOperator.pow(this, index);
    }

    public Tensor square() {
        return TensorOperator.square(this);
    }

    public Tensor reshape(int[] shape) {
        return  TensorOperator.reshape(this, shape);
    }

    public Tensor transpose() {
        return  TensorOperator.transpose(this);
    }

    public Tensor sum() {
        return TensorUtils.sum(this, TensorUtils.RANK_MAX);
    }

    public Tensor sum(int axis) {
        return TensorUtils.sum(this, axis);
    }

    public Tensor sumTo(int[] shape) {
        return TensorUtils.sumTo(this, shape);
    }

    public Tensor broadcastTo(int[] shape) {
        return TensorUtils.broadcastTo(this, shape);
    }

}