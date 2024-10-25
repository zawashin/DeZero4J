package dezero4jv1.step.step42;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step40 {

    public static void main(String[] args) {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[1] = new Variable(1);
        System.out.println("x");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        Function plus = new Plus();
        Variable y = plus.forward(xs)[0];
        System.out.println(y);
        y.backward(false, true);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
    }
}
