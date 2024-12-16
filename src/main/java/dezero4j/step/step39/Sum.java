package dezero4j.step.step39;

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
        System.out.println(this.axis);
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{Utils.sum(xs[0], axis)};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        //if(this.axis != -1||this.axis == Tensor.RANK_MAX) {
        if (gys[0].getRank() > 0) {
            System.err.println("Not Implemented");
            throw new RuntimeException("Not Implemented Yet");
        }
        //return new Variable[]{gys[0].broadcastTo(inputs[0].getShapes())};
        Tensor gy0 = Utils.fill(gys[0].getValues()[0], inputs[0].getShapes());
        return new Variable[]{new Variable(gy0)};
    }

    @Override
    public Sum clone() {
        Sum clone = (Sum) super.clone();
        clone.axis = this.axis;
        return clone;
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

        double[][] matrix = new double[3][5];
        System.out.println("2D");
        t = new Tensor(matrix);
        n = 0;
        for (int i = 0; i < t.getLength(); i++) {
            t.getValues()[n] = n++;
        }
        x = new Variable(t);
        System.out.println(x);
        y = (x.multiply(Math.PI)).sum();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();
        System.out.println(x.sum(0));
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();
        System.out.println(x.sum(1));
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        y = x.sum();
        y.backward(false, true);
        System.out.println(x.grad);

        /*
         */
    }

}
