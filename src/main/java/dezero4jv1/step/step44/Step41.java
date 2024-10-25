package dezero4jv1.step.step44;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step41 {

    public static void main(String[] args) {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        //xs[1] = new Variable(new double[]{1, 2, 3});
        Variable xt= new Variable(new double[]{1, 2, 3});
        xs[1] = xt.transpose();
        System.out.println("x");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        Function dot = new Dot();
        Variable y = dot.forward(xs)[0];
        System.out.println(y);
        y.backward(true, true);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
        System.out.println(xt.grad);

        xs[0] = new Variable(new double[]{2, 3});
        xs[1] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println("x");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        y = dot.forward(xs)[0];
        System.out.println("y");
        System.out.println(y);
        xs[0].clearGrad();
        xs[1].clearGrad();
        y.backward(true, true);
        System.out.println("x");
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);

        xs[0] = new Variable(new double[]{1, 2, 3});
        xs[1] = xs[0].transpose();
        System.out.println("x");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        y = dot.forward(xs)[0];
        System.out.println("y");
        System.out.println(y);
        xs[0].clearGrad();
        xs[1].clearGrad();
        y.backward(true, true);
        System.out.println("x");
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
    }
}
