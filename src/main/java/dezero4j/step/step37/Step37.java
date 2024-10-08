package dezero4j.step.step37;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step37 {

    public static void main(String[] args) {
        Variable x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println(x);
        Variable y = x.sin();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(y);
        System.out.println(x.grad);
        Variable y2 = x.cos();
        System.out.println(y2);
        Variable c = new Variable(new double[][]{{10, 20, 30}, {40, 50, 60}});
        System.out.println(c);
        Variable t = x.plus(c);
        System.out.println(t);
        //y = t.sum();
        //System.out.println(y.grad);
        //System.out.println(t.grad);
        //System.out.println(c.grad);

    }
}
