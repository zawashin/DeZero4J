package dezero4j.step.step34.step39;

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
        shape = new int[TensorOperator.RANK_MAX];
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
        if(array2d.length == 1) {
            if(array2d[0].length == 1) {
                rank = 0;
            } else {
                // 行ベクトル
                rank = 1;
            };
        } else {
            if(array2d[0].length == 1) {
                // 列ベクトル
                rank = 1;
            } else {
                rank = 2;
            };
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
            throw new RuntimeException(TensorOperator.ERROR_LENGTH);
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
            throw new RuntimeException(TensorOperator.ERROR_LENGTH);
        }
        if (shape[0] == 1) {
            if (shape[1] == 1) {
                rank = 0;
            } else {
                rank = 1;
            }
        } else  {
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
        return TensorOperator.toString(this);
    }

    public int index(int i, int j) {
        return TensorOperator.index(this, i, j);
    }

    public double getValue(int i, int j) {
        return TensorOperator.getValue(this, i, j);
    }

    public void setValue(double value) {
        TensorOperator.setValue(this, value);
    }

    public void setValue(double value, int i) {
        values[i] = value;
    }

    public void setValue(double value, int i, int j) {
        TensorOperator.setValue(this, value, i, j);
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

    public Tensor multiply(Tensor other) {
        double[] xArray0 = values;
        double[] xArray1 = other.values;
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            y[i] = xArray0[i] * xArray1[i];
        }
        return new Tensor(y, shape);

    }

    public Tensor divide(Tensor other) {
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

    public Tensor transpose() {
        int[] shape = new int[TensorOperator.RANK_MAX];
        shape[0] = this.shape[1];
        shape[1] = this.shape[0];
        return new Tensor(values, shape);
    }

    public Tensor sum() {
        return TensorOperator.sum(this, TensorOperator.RANK_MAX);
    }

    public Tensor sum(int axis) {
        return TensorOperator.sum(this, axis);
    }

    public Tensor sumTo(int[] shape) {
        return TensorOperator.sumTo(this, shape);
    }

    public Tensor broadcastTo(int[] shape) {
        return TensorOperator.broadcastTo(this, shape);
    }

}