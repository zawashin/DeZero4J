package dezero4jv1.step.step42;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step39 {

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println("x");
        System.out.println(x);
        xs[0] = x;
        Function f = new SumTo(new int[]{1, 3});
        Variable[] ys = f.forward(xs);
        Variable y = ys[0];
        y.backward(false, true);
        System.out.println(x.grad);
        f = new Sum(0);
        ys = f.forward(xs);
        System.out.println(ys[0]);
        f = new Sum(1);
        ys = f.forward(xs);
        System.out.println(ys[0]);

    }
}
