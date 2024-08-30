/**
 * DeepNetts is pure Java Deep Learning Library with support for Backpropagation
 * based learning and image recognition.
 * <p>
 * Copyright (C) 2017  Zoran Sevarac <sevarac@gmail.com>
 * <p>
 * This file is part of DeepNetts.
 * <p>
 * DeepNetts is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <https://www.gnu.org/licenses/>.package
 * deepnetts.core;
 */
package tensor4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;

/**
 * This class represents multidimensional array/matrix (can be 1D, 2D, 3D or 4D).
 *
 * @author Zoran Sevarac
 */
public class Tensor implements Serializable {


    private static final long serialVersionUID = -2345745004528761209L;


    // tensor dimensions - better use shape
    private final int cols, rows, depth, fourthDim, dimensions;
    private final int[] shape = new int[4];
    private int rank;
    private int size;

    /**
     * Values stored in this tensor make it final , only input layer and tests
     * sets values
     */
    private double values[];

    /**
     * Creates a single row tensor with specified values.
     *
     * @param values values of column tensor
     */
    public Tensor(final double... values) {
        this.rows = 1;
        this.cols = values.length;
        this.depth = 1;
        this.fourthDim = 1;
        this.dimensions = 1;
        this.values = values;
    }

    /**
     * Creates a 2D tensor / matrix with specified values.
     *
     * @param vals
     */
    public Tensor(final double[][] vals) {
        this.rows = vals.length;
        this.cols = vals[0].length;
        this.depth = 1;
        this.fourthDim = 1;
        this.dimensions = 2;
        this.values = new double[rows * cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                set(row, col, vals[row][col]);
            }
        }
    }

    /**
     * Creates a 3D tensor from specified 3D array
     *
     * @param vals 2D array of tensor values
     */
    public Tensor(final double[][][] vals) {
        this.depth = vals.length;
        this.rows = vals[0].length;
        this.cols = vals[0][0].length;

        this.fourthDim = 1;
        this.dimensions = 3;
        this.values = new double[rows * cols * depth];

        for (int z = 0; z < depth; z++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    set(row, col, z, vals[z][row][col]);
                }
            }
        }
    }

    public Tensor(final double[][][][] vals) {

        this.fourthDim = vals.length;
        this.depth = vals[0].length;
        this.rows = vals[0][0].length;
        this.cols = vals[0][0][0].length;

        this.dimensions = 4;
        this.values = new double[rows * cols * depth * fourthDim];

        for (int f = 0; f < fourthDim; f++) {
            for (int z = 0; z < depth; z++) {
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        set(row, col, z, f, vals[f][z][row][col]);
                    }
                }
            }
        }
    }

    /**
     * Creates an empty single row tensor with specified number of columns.
     * TODO: this should be rows!!!
     * @param cols number of columns
     */
    public Tensor(int cols) {
        if (cols < 0) {
            throw new IllegalArgumentException("Number of cols cannot be negative: " + cols);
        }

        this.cols = cols;
        this.rows = 1;
        this.depth = 1;
        this.fourthDim = 1;
        this.dimensions = 1;
        values = new double[cols];
    }

    public Tensor(int cols, double val) {
        if (cols < 0) {
            throw new IllegalArgumentException("Number of cols cannot be negative: " + cols);
        }

        this.cols = cols;
        this.rows = 1;
        this.depth = 1;
        this.fourthDim = 1;
        this.dimensions = 1;
        values = new double[cols];

        for (int i = 0; i < values.length; i++) {
            values[i] = val;
        }
    }

    /**
     * Creates a tensor with specified number of rows and columns.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public Tensor(int rows, int cols) {
        if (rows < 0) {
            throw new IllegalArgumentException("Number of rows cannot be negative: " + rows);
        }
        if (cols < 0) {
            throw new IllegalArgumentException("Number of cols cannot be negative: " + cols);
        }

        this.rows = rows;
        this.cols = cols;
        this.depth = 1;
        this.fourthDim = 1;
        this.dimensions = 2;
        values = new double[rows * cols];
    }

    public Tensor(int rows, int cols, double[] values) {
        if (rows < 0) {
            throw new IllegalArgumentException("Number of rows cannot be negative: " + rows);
        }
        if (cols < 0) {
            throw new IllegalArgumentException("Number of cols cannot be negative: " + cols);
        }
        if (rows * cols != values.length) {
            throw new IllegalArgumentException("Number of values does not match tensor dimensions! " + values.length);
        }

        this.rows = rows;
        this.cols = cols;
        this.depth = 1;
        this.fourthDim = 1;
        this.dimensions = 2;

        this.values = values;
    }

    /**
     * Creates a 3D tensor with specified number of rows, cols and depth.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param depth tensor depth
     */
    public Tensor(int rows, int cols, int depth) { // trebalo bi depth, rows, cols
        if (rows < 0) {
            throw new IllegalArgumentException("Number of rows cannot be negative: " + rows);
        }
        if (cols < 0) {
            throw new IllegalArgumentException("Number of cols cannot be negative: " + cols);
        }
        if (depth < 0) {
            throw new IllegalArgumentException("Depth cannot be negative: " + depth);
        }

        this.rows = rows;
        this.cols = cols;
        this.depth = depth;
        this.fourthDim = 1;
        this.dimensions = 3;
        this.values = new double[rows * cols * depth];
    }

    // cols, rows, 3rd, 4th?
    public Tensor(int rows, int cols, int depth, int fourthDim) { // trebalo bi fourthDim, depth, rows, cols
        if (rows < 0) {
            throw new IllegalArgumentException("Number of rows cannot be negative: " + rows);
        }
        if (cols < 0) {
            throw new IllegalArgumentException("Number of cols cannot be negative: " + cols);
        }
        if (depth < 0) {
            throw new IllegalArgumentException("Depth cannot be negative: " + depth);
        }
        if (fourthDim < 0) {
            throw new IllegalArgumentException("fourthDim cannot be negative: " + fourthDim);
        }

        this.rows = rows;
        this.cols = cols;
        this.depth = depth;
        this.fourthDim = fourthDim;
        this.dimensions = 4;
        this.values = new double[rows * cols * depth * fourthDim];
    }

    public Tensor(int rows, int cols, int depth, int fourthDim, double[] values) {
        if (rows < 0) {
            throw new IllegalArgumentException("Number of rows cannot be negative: " + rows);
        }
        if (cols < 0) {
            throw new IllegalArgumentException("Number of cols cannot be negative: " + cols);
        }
        if (depth < 0) {
            throw new IllegalArgumentException("Depth cannot be negative: " + depth);
        }
        if (fourthDim < 0) {
            throw new IllegalArgumentException("fourthDim cannot be negative: " + fourthDim);
        }

        this.rows = rows;
        this.cols = cols;
        this.depth = depth;
        this.fourthDim = fourthDim;
        this.dimensions = 4;
        this.values = values;
    }

    public Tensor(int rows, int cols, int depth, double[] values) {
        if (rows < 0) {
            throw new IllegalArgumentException("Number of rows cannot be negative: " + rows);
        }
        if (cols < 0) {
            throw new IllegalArgumentException("Number of cols cannot be negative: " + cols);
        }
        if (depth < 0) {
            throw new IllegalArgumentException("Depth cannot be negative: " + depth);
        }
        if (rows * cols * depth != values.length) {
            throw new IllegalArgumentException("Number of values does not match tensor dimensions! " + values.length);
        }

        this.cols = cols;
        this.rows = rows;
        this.depth = depth;
        this.fourthDim = 1;
        this.dimensions = 3;
        this.values = values;
    }

    private Tensor(Tensor t) {
        this.cols = t.cols;
        this.rows = t.rows;
        this.depth = t.depth;
        this.fourthDim = t.fourthDim;
        this.dimensions = t.dimensions;
        values = new double[t.values.length];

        System.arraycopy(t.values, 0, values, 0, t.values.length);
    }

    /**
     * Gets value at specified index position.
     *
     * @param idx
     * @return
     */
    public final double get(final int idx) {
        return values[idx];
    }

    /**
     * Sets value at specified index position.
     *
     * @param idx
     * @param val
     * @return
     */
    public final double set(final int idx, final double val) {
        return values[idx] = val;
    }

    /**
     * Returns matrix value at row, col
     *
     * @param col
     * @param row
     * @return value at [row, col]
     */
    public final double get(final int row, final int col) {
        final int idx = row * cols + col;
        return values[idx];
    }

    /**
     * Sets matrix value at specified [row, col] position
     *
     * @param row matrix roe
     * @param col matrix col
     * @param val value to set
     */
    public final void set(final int row, final int col, final double val) {
        final int idx = row * cols + col;
        values[idx] = val;
    }

    /**
     * Returns value at row, col, z
     *
     * @param col
     * @param row
     * @param z
     * @return
     */
    public final double get(final int row, final int col, final int z) {
        final int idx = z * cols * rows + row * cols + col;
        return values[idx];
    }

    public final void set(final int row, final int col, final int z, final double val) {
        final int idx = z * cols * rows + row * cols + col;
        values[idx] = val;
    }

    public final double get(final int row, final int col, final int z, final int fourth) {
        final int idx = fourth * rows * cols * depth + z * rows * cols + row * cols + col;
        return values[idx];
    }

    public final void set(final int row, final int col, final int z, final int fourth, final double val) {
        final int idx = fourth * rows * cols * depth + z * rows * cols + row * cols + col;
        values[idx] = val;
    }

    public final double getWithStride(final int[] idxs) {
        final int idx = idxs[0] * shape[1] * shape[2] * shape[3] + idxs[1] * shape[2] * shape[3] + idxs[2] * shape[3] + idxs[3];
        return values[idx];
    }

    public final double[] getValues() {
        return values;
    }

    public final void setValues(final double... values) {
//        if (values.length != this.values.length) throw new DeepNettsException("Arrays are not of same size!");
        this.values = values;
    }

    public final void copyFrom(final double[] src) {
        System.arraycopy(src, 0, values, 0, values.length);
    }

    public final int getCols() {
        return cols;
    }

    public final int getRows() {
        return rows;
    }

    public final int getDepth() {
        return depth;
    }

    public final int getFourthDim() {
        return fourthDim;
    }

    public final int getDimensions() {
        return dimensions;
    }

    public final int size() {
        return values.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (((i + 1) % cols == 0) && (i < values.length - 1)) {
                sb.append("; ");
            } else if (i < values.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        return sb.toString();
    }

    public final void add(final int idx, final double value) {
        values[idx] += value;
    }

    /**
     * Adds specified value to matrix value at position x, y
     *
     * @param col
     * @param row
     * @param value
     */
    public final void add(final int row, final int col, final double value) {
        final int idx = row * cols + col;
        values[idx] += value;
    }

    public final void add(final int row, final int col, final int z, final double value) {
        final int idx = z * cols * rows + row * cols + col;
        values[idx] += value;
    }

    public final void add(final int row, final int col, final int z, final int fourth, final double value) {
        final int idx = fourth * cols * rows * depth + z * cols * rows + row * cols + col;
        values[idx] += value;
    }


    /**
     * Adds specified tensor t to this tensor.
     *
     * @param t tensor to add
     */
    public final void add(Tensor t) {
        for (int i = 0; i < values.length; i++) {
            values[i] += t.values[i];
        }
    }

    public final void sub(final int row, final int col, final double value) {
        final int idx = row * cols + col;
        values[idx] -= value;
    }

    public final void sub(final int row, final int col, final int z, final double value) {
        final int idx = z * rows * cols + row * cols + col;
        values[idx] -= value;
    }

    public final void sub(final int row, final int col, final int z, final int fourth, final double value) {
        final int idx = fourth * rows * cols * depth + z * rows * cols + row * cols + col;
        values[idx] -= value;
    }

    /**
     * Subtracts specified tensor t from this tensor.
     *
     * @param t tensor to subtract
     */
    public final void sub(final Tensor t) {
        for (int i = 0; i < values.length; i++) {
            values[i] -= t.values[i];
        }
    }

    public final void sub(final double val) {
        for (int i = 0; i < values.length; i++) {
            values[i] -= val;
        }
    }

    /**
     * Subtracts tensor t2 from t1. The result is t1.
     *
     * @param t1
     * @param t2
     */
    public final static void sub(final Tensor t1, final Tensor t2) {
        for (int i = 0; i < t1.values.length; i++) {
            t1.values[i] -= t2.values[i];
        }
    }

    /**
     * Divide all values in this tensor with specified value.
     *
     * @param value
     */
    public final void div(final double value) {
        for (int i = 0; i < values.length; i++) {
            values[i] /= value;
        }
    }

    // element wise division
    public final void div(final double[] divisors) {
        for (int i = 0; i < values.length; i++) {
            values[i] /= divisors[i];
        }
    }

    /**
     * Fills the entire tensor with specified value.
     *
     * @param value value used to fill tensor
     */
    public final void fill(final double value) {
        for (int i = 0; i < values.length; i++) {
            values[i] = value;
        }
    }

    public static final void fill(final double[] array, final double val) {
        for (int i = 0; i < array.length; i++) {
            array[i] = val;
        }
    }


    public final void div(Tensor t) {
        for (int i = 0; i < values.length; i++) {
            this.values[i] = this.values[i] / t.values[i];
        }
    }

    // TODO: fix number of dimensions
    public Tensor copy() {
        Tensor newTensor = new Tensor(rows, cols, depth, fourthDim);
        System.arraycopy(this.values, 0, newTensor.values, 0, this.values.length);
        return newTensor;
    }

    // TODO: also set dimensions for dst
    public static final void copy(final Tensor src, final Tensor dest) {
        System.arraycopy(src.values, 0, dest.values, 0, src.values.length);
    }

    public static final void copy(final double[] src, final double[] dest) {
        System.arraycopy(src, 0, dest, 0, src.length);
    }

    public void apply(Function<Double, Double> f) {
        for (int i = 0; i < values.length; i++) {
            values[i] = f.apply(values[i]);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tensor other = (Tensor) obj;
        if (this.cols != other.cols) {
            return false;
        }
        if (this.rows != other.rows) {
            return false;
        }
        if (this.depth != other.depth) {
            return false;
        }
        if (this.fourthDim != other.fourthDim) {
            return false;
        }
        if (this.dimensions != other.dimensions) {
            return false;
        }
        if (!Arrays.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.cols;
        hash = 41 * hash + this.rows;
        hash = 41 * hash + this.depth;
        hash = 41 * hash + this.fourthDim;
        hash = 41 * hash + this.dimensions;
        hash = 41 * hash + Arrays.hashCode(this.values);
        return hash;
    }

    public boolean equals(Tensor t2, double delta) {
        double[] arr2 = t2.getValues();

        for (int i = 0; i < values.length; i++) {
            if (Math.abs(values[i] - arr2[i]) > delta) {
                return false;
            }
        }
        return true;
    }

// add clone using apache clone builder

    public static String valuesAsString(Tensor[] tensors) {
        StringBuilder sb = new StringBuilder();

        for (Tensor t : tensors) {
            sb.append(t.toString());
        }

        return sb.toString();
    }

    /**
     * Sets tensor values from csv string.
     *
     * @param values csv string with values
     */
    public void setValuesFromString(String values) {
        String[] strArr = values.split(",");
        for (int i = 0; i < strArr.length; i++) {
            this.values[i] = Float.parseFloat(strArr[i]);
        }
    }

    /**
     * Factory method for creating tensor instance,
     *
     * @param rows
     * @param cols
     * @param values
     * @return
     */
    public static Tensor create(int rows, int cols, double[] values) {
        return new Tensor(rows, cols, values);
    }

    public static Tensor create(int rows, int cols, int depth, double[] values) {
        return new Tensor(rows, cols, depth, values);
    }

    public static Tensor create(int rows, int cols, int depth, int fourthDim, double[] values) {
        return new Tensor(rows, cols, depth, fourthDim, values);
    }

    /**
     * Returns sum of abs values of this tensor - L1 norm
     *
     * @return L1 norm
     */
    public double sumAbs() {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += Math.abs(values[i]);
        }
        return sum;
    }

    /**
     * Returns sum of sqr values of this tensor - L2 norm
     *
     * @return L2 norm
     */
    public double sumSqr() {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i] * values[i];
        }
        return sum;
    }

    // works for 2d tensors
    public void randomize() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                //values[r * cols + c] = RandomGenerator.getDefault().nextFloat();
            }
        }
    }

    public void multiplyElementWise(Tensor tensor2) {
        for (int i = 0; i < values.length; i++) {
            values[i] *= tensor2.values[i];
        }
    }

    public void multiply(double m) {
        for (int i = 0; i < values.length; i++) {
            values[i] *= m;
        }
    }

    public void sqrt() {
        for (int i = 0; i < values.length; i++) {
            values[i] = (double) Math.sqrt(values[i]);
        }
    }

}
