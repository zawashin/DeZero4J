package step.step49;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class DataSet implements Serializable {
    @Serial
    private static final long serialVersionUID = 7872471894346034525L;
    private final double[][] x;
    private final double[][] target;

    public DataSet(double[][] x, double[][] target) {
        if (x.length != target.length) {
            throw new IllegalArgumentException("The number of data is different between x and t.");
        }
        this.x = new double[x.length][];
        for (int i = 0; i < x.length; i++) {
            this.x[i] = x[i].clone();
        }
        this.target = new double[target.length][];
        for (int i = 0; i < target.length; i++) {
            this.target[i] = target[i].clone();
        }
    }

    public DataSet(double[][] x, double[] target) {
        if (x.length != target.length) {
            throw new IllegalArgumentException("The number of data is different between x and t.");
        }
        this.x = new double[x.length][];
        for (int i = 0; i < x.length; i++) {
            this.x[i] = x[i].clone();
        }
        this.target = new double[1][target.length];
        this.target[0] = target.clone();
    }

    public DataSet(double[][] x, int[][] target) {
        if (x.length != target.length) {
            throw new IllegalArgumentException("The number of data is different between x and t.");
        }
        this.x = new double[x.length][];
        for (int i = 0; i < x.length; i++) {
            this.x[i] = x[i].clone();
        }
        this.target = new double[target.length][];

        for (int i = 0; i < target.length; i++) {
            this.target[i] = Arrays.stream(target[i]).asDoubleStream().toArray();
        }
    }

    public DataSet(double[][] x, int[] target) {
        if (x.length != target.length) {
            throw new IllegalArgumentException("The number of data is different between x and t.");
        }
        this.x = new double[x.length][];
        for (int i = 0; i < x.length; i++) {
            this.x[i] = x[i].clone();
        }
        this.target = new double[1][target.length];
        this.target[0] = Arrays.stream(target).asDoubleStream().toArray();
    }


    public double[][] getX() {
        return x;
    }

    public double[][] getTarget() {
        return target;
    }

    public double[] getTarget(int n) {
        return target[n];
    }

    public int[][] getTargetAsInt() {
        int[][] result = new int[target.length][];
        for (int i = 0; i < target.length; i++) {
            result[i] = Arrays.stream(target[i])
                    .mapToInt(d -> (int) d) // キャスト
                    .toArray();
        }
        return result;
    }

    public int[] getTargetAsInt(int n) {
        return Arrays.stream(target[n])
                .mapToInt(d -> (int) d) // キャスト
                .toArray();
    }

}
