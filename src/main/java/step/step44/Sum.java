package step.step44;

import tensor4j.Tensor;
import tensor4j.Utils;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sum extends Function {

    private int axis;

    public Sum() {
        this(-1);
    }

    public Sum(int axis) {
        this.axis = axis;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x;
        Variable y;
        Tensor t;
        int n;
        System.out.println("1D");
        double[] vector = new double[5];
        t = new Tensor(vector);
        n = 0;
        for (int i = 0; i < t.getLength(); i++) {
            t.getValues()[n] = n++;
        }
        x = new Variable(t);
        System.out.println(x);
        y = (x.multiply(2)).sum();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.getGrad());

        /*
         */

        double[][] matrix = new double[3][5];
        System.out.println("2D");
        t = new Tensor(matrix);
        n = 0;
        for (int i = 0; i < t.getLength(); i++) {
            t.getValues()[n] = n++;
        }
        x = new Variable(t);
        y = (x.multiply(2)).sum();
        System.out.println(x);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();
        y = (x.multiply(3)).sum(0);
        y.backward(false, true);
        System.out.println(x);
        System.out.println(y);
        System.out.println(x.grad);
        x.clearGrad();
        y = (x.multiply(4)).sum(1);
        y.backward(false, true);
        System.out.println(x);
        System.out.println(y);
        System.out.println(x.grad);

        /*
         */
    }

    public void setAxis(int axis) {
        this.axis = axis;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{Utils.sum(xs[0], axis)};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Tensor gy0 = null;
        double[] values;
        //if(this.axis != -1||this.axis == Tensor.RANK_MAX) {
        if (gys[0].getRank() == 0) {
            gy0 = Utils.fill(gys[0].getValues()[0], inputs[0].getShape());
        } else if (gys[0].getRank() == 1) {
            if (axis == 0) {
                gy0 = new Tensor(inputs[0].getShape()[0], gys[0].getLength());
                values = gys[0].getValues();
                for (int i = 0; i < gy0.getShape(0); i++) {
                    for (int j = 0; j < gy0.getShape(1); j++) {
                        gy0.setValue(values[j], i, j);
                    }
                }
            } else if (axis == 1) {
                gy0 = new Tensor(gys[0].getLength(), inputs[0].getShape()[1]);
                values = gys[0].getValues();
                for (int i = 0; i < gy0.getShape(0); i++) {
                    for (int j = 0; j < gy0.getShape(1); j++) {
                        gy0.setValue(values[i], i, j);
                    }
                }
            } else {
                System.err.println(Utils.ERROR_RANK);
                throw new RuntimeException(Utils.ERROR_RANK);
            }
        } else {
            System.err.println(Utils.ERROR_RANK);
            throw new RuntimeException(Utils.ERROR_RANK);
        }
        return new Variable[]{new Variable(gy0)};
        //return new Variable[]{gys[0].broadcastTo(inputs[0].getShape())};
    }

}
