package step.step49;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class DataSet implements Serializable {
    @Serial
    private static final long serialVersionUID = 7872471894346034525L;
    private double[][] x;
    private int[] t;

    public DataSet(double[][] x, int[] t) {
        this.x = x;
        this.t = t;
    }

    public double[][] getX() {
        return x;
    }

    public int[] getT() {
        return t;
    }

    public int getDataSize() {
        return x.length;
    }
}