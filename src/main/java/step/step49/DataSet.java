package step.step49;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class DataSet implements Serializable {
    @Serial
    private static final long serialVersionUID = 7872471894346034525L;
    private double[][] x;
    private double[][] t;

    public DataSet(double[][] x, double[][] t) {
        this.x = new double[x.length][];
        for (int i = 0; i < x.length; i++) {
            this.x[i] = x[i].clone();
        }
        this.t = new double[t.length][];
        for (int i = 0; i < t.length; i++) {
            this.t[i] = t[i].clone();
        }
    }

    public DataSet(double[][] x, double[] t) {
        this.x = new double[x.length][];
        for (int i = 0; i < x.length; i++) {
            this.x[i] = x[i].clone();
        }
        this.t = new double[1][t.length];
        this.t[0] = t.clone();
    }

    public DataSet(double[] x, double[] t) {
        this.x = new double[1][x.length];
        this.x[0] = x.clone();
        this.t = new double[1][t.length];
        this.t[0] = t.clone();
    }

    public double[][] getX() {
        return x;
    }

    public double[][] getT() {
        return t;
    }

    public double[] getT(int n) {
        return t[n];
    }
}
